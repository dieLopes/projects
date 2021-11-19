package com.inter.desafiointer.service;

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
import java.util.UUID;

@Service
public class OrderPersistenceService {

    private final OrderRepository orderRepository;
    private final CompanyRepository companyRepository;
    private final WalletRepository walletRepository;

    public OrderPersistenceService(OrderRepository orderRepository,
                                   CompanyRepository companyRepository,
                                   WalletRepository walletRepository) {
        this.orderRepository = orderRepository;
        this.companyRepository = companyRepository;
        this.walletRepository = walletRepository;
    }

    public Order save (Order order) {
        Company company = companyRepository.findByCode(order.getCode())
                .orElseThrow(() -> new BadRequestException("Company not found with code " + order.getCode()));
        Wallet wallet = walletRepository.findById(order.getWallet().getId())
                .orElseThrow(() -> new BadRequestException("Wallet not found with id " + order.getWallet().getId()));
        order.setId(UUID.randomUUID().toString());
        order.setDate(LocalDateTime.now());
        order.setCompany(company);
        order.setUnitPrice(company.getPrice());
        order.setTotalPrice(company.getPrice().multiply(new BigDecimal(order.getAmount())));
        wallet.setBalance(wallet.getBalance().add(order.getTotalPrice()));
        order.setWallet(wallet);
        return orderRepository.save(order);
    }
}
