package com.inter.desafiointer.service;

import com.inter.desafiointer.builder.WalletStockBuilder;
import com.inter.desafiointer.domain.Order;
import com.inter.desafiointer.domain.OrderStatus;
import com.inter.desafiointer.domain.OrderType;
import com.inter.desafiointer.domain.WalletStock;
import com.inter.desafiointer.repository.WalletStockRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class WalletStockPersistenceService {

    private final WalletStockRepository walletStockRepository;

    public WalletStockPersistenceService(WalletStockRepository walletStockRepository) {
        this.walletStockRepository = walletStockRepository;
    }

    protected WalletStock save (WalletStock walletStock) {
        walletStock.setId(UUID.randomUUID().toString());
        return walletStockRepository.save(walletStock);
    }

    protected WalletStock update (WalletStock walletStock) {
        return walletStockRepository.save(walletStock);
    }

    public CompletableFuture<List<Order>> processOrder(List<Order> orders) {
        List<Order> newOrders = new ArrayList<>();
        orders.forEach(order -> {
            if (OrderType.BUY.equals(order.getType())) {
                newOrders.add(processBuyOrder(order));
            } else {
                newOrders.add(processSellOrder(order));
            }
        });
        return CompletableFuture.completedFuture(newOrders);
    }

    private Order processBuyOrder (Order order) {
        AtomicReference<WalletStock> walletStock = new AtomicReference<>();
        walletStockRepository.findByCompanyCodeAndWalletId(order.getCompany().getCode(), order.getWallet().getId())
                .ifPresentOrElse(stock -> {
                    stock.setAmount(stock.getAmount() + order.getAmount());
                    stock.setBalance(stock.getBalance().add(order.getTotalPrice()));
                    stock.setWallet(order.getWallet());
                    walletStock.set(update(stock));
                    }, () ->
                        walletStock.set(save(WalletStockBuilder.of()
                                .companyCode(order.getCompany().getCode())
                                .amount(order.getAmount())
                                .wallet(order.getWallet())
                                .balance(order.getTotalPrice())
                                .build()))
                );
        order.setStatus(OrderStatus.OK);
        return order;
    }

    private Order processSellOrder (Order order) {
        Optional<WalletStock> walletStock = walletStockRepository.findByCompanyCodeAndWalletId(
                order.getCompany().getCode(), order.getWallet().getId());
        if (walletStock.isEmpty()) {
            order.setStatus(OrderStatus.CANCELLED);
        } else {
            WalletStock stock = walletStock.get();
            if (stock.getAmount() < order.getAmount()) {
                order.setStatus(OrderStatus.CANCELLED);
                return order;
            }
            stock.setAmount(stock.getAmount() - order.getAmount());
            stock.setBalance(stock.getBalance().subtract(order.getTotalPrice()));
            stock.setWallet(order.getWallet());
            update(stock);
            order.setStatus(OrderStatus.OK);
        }
        return order;
    }
}
