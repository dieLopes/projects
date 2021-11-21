package com.inter.desafiointer.service;

import com.inter.desafiointer.builder.OrderBuilder;
import com.inter.desafiointer.domain.Company;
import com.inter.desafiointer.domain.Order;
import com.inter.desafiointer.domain.Wallet;
import com.inter.desafiointer.exception.BadRequestException;
import com.inter.desafiointer.repository.CompanyRepository;
import com.inter.desafiointer.repository.OrderRepository;
import com.inter.desafiointer.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.inter.desafiointer.domain.CompanyStatus.ACTIVE;
import static com.inter.desafiointer.domain.OrderStatus.PENDING;
import static com.inter.desafiointer.domain.OrderType.BUY;

@Service
public class OrderPersistenceService {

    private final OrderRepository orderRepository;
    private final CompanyRepository companyRepository;
    private final WalletRepository walletRepository;
    private final WalletStockPersistenceService walletStockPersistenceService;

    public OrderPersistenceService(OrderRepository orderRepository,
                                   CompanyRepository companyRepository,
                                   WalletRepository walletRepository,
                                   WalletStockPersistenceService walletStockPersistenceService) {
        this.orderRepository = orderRepository;
        this.companyRepository = companyRepository;
        this.walletRepository = walletRepository;
        this.walletStockPersistenceService = walletStockPersistenceService;
    }

    public Order save (Order order) {
        validateFields(order);
        Company company = companyRepository.findByCodeAndStatus(order.getCode(), ACTIVE)
                .orElseThrow(() -> new BadRequestException("Company not found with code " + order.getCode()));
        Wallet wallet = walletRepository.findByUserCpf(order.getCpf())
                .orElseThrow(() -> new BadRequestException("Wallet not found for user " + order.getCpf()));
        order.setId(UUID.randomUUID().toString());
        order.setDate(LocalDateTime.now());
        order.setCompany(company);
        order.setUnitPrice(company.getPrice());
        order.setTotalPrice(company.getPrice().multiply(new BigDecimal(order.getAmount())));
        order.setWallet(wallet);
        Order savedOrder = orderRepository.save(order);
        processOrder(List.of(savedOrder));
        return savedOrder;
    }

    private void validateFields (Order order) {
        if (order.getAmount() <= 0 ) {
            throw new BadRequestException("Amount should be greater than 0");
        }
        if (order.getCode() == null || order.getCode().isEmpty()) {
            throw new BadRequestException("Code is invalid");
        }
    }

    public List<Order> createRandomOrders (String cpf, BigDecimal total) {
        Wallet wallet = walletRepository.findByUserCpf(cpf)
                .orElseThrow(() -> new BadRequestException("Wallet not found for user " + cpf));
        List<Company> comps = companyRepository.findByStatusOrderByPriceAsc(ACTIVE);
        if (comps.isEmpty()) {
            throw new BadRequestException("There aren't companies to buy");
        }

        AtomicReference<BigDecimal> allocated = new AtomicReference<>(BigDecimal.ZERO);
        AtomicReference<BigDecimal> change = new AtomicReference<>(total);
        AtomicReference<BigDecimal> minPrice = new AtomicReference<>(comps.get(0).getPrice());
        Map<Company, Integer> mapOrders = new HashMap<>();
        while(allocated.get().compareTo(total) < 0 && change.get().compareTo(minPrice.get()) > 0) {
            comps.forEach(company -> {
                BigDecimal newAllocated = allocated.get().add(company.getPrice());
                if (newAllocated.compareTo(total) < 0) {
                    if (mapOrders.containsKey(company)) {
                        mapOrders.put(company, mapOrders.get(company) + 1);
                    } else {
                        mapOrders.put(company, 1);
                    }
                    allocated.set(newAllocated);
                    change.set(total.subtract(allocated.get()));
                }
            });
        }
        List<Order> orders = mapOrders.entrySet().stream()
                .map(company ->
                        orderRepository.save(OrderBuilder.of()
                                .id(UUID.randomUUID().toString())
                                .amount(company.getValue())
                                .company(company.getKey())
                                .wallet(wallet)
                                .status(PENDING)
                                .date(LocalDateTime.now())
                                .type(BUY)
                                .unitPrice(company.getKey().getPrice())
                                .totalPrice(company.getKey().getPrice().multiply(new BigDecimal(company.getValue())))
                                .build()))
                .collect(Collectors.toList());
        processOrder(orders);
        return orders;
    }

    private void processOrder (List<Order> order) {
        CompletableFuture
                .supplyAsync(() ->
                        walletStockPersistenceService.processOrder(order))
                .whenComplete((result, ex) -> {
                    try {
                        result.get().forEach(orderRepository::save);
                    } catch (InterruptedException | ExecutionException ignored) {}
                });
    }
}
