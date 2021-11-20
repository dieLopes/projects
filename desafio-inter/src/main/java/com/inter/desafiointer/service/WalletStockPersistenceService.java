package com.inter.desafiointer.service;

import com.inter.desafiointer.builder.WalletStockBuilder;
import com.inter.desafiointer.domain.Order;
import com.inter.desafiointer.domain.OrderType;
import com.inter.desafiointer.domain.WalletStock;
import com.inter.desafiointer.exception.BadRequestException;
import com.inter.desafiointer.repository.WalletStockRepository;
import org.springframework.stereotype.Service;

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

    public WalletStock save (WalletStock walletStock) {
        walletStock.setId(UUID.randomUUID().toString());
        return walletStockRepository.save(walletStock);
    }

    public WalletStock update (WalletStock walletStock) {
        return walletStockRepository.save(walletStock);
    }

    public CompletableFuture<WalletStock> processOrder(Order order) {
        if (OrderType.BUY.equals(order.getType())) {
            return processBuyOrder(order);
        } else {
            return processSellOrder(order);
        }
    }

    private CompletableFuture<WalletStock> processBuyOrder (Order order) {
        AtomicReference<WalletStock> walletStock = new AtomicReference<>();
        walletStockRepository.findByCompanyCode(order.getCompany().getCode()).ifPresentOrElse(stock -> {
            stock.setAmount(stock.getAmount() + order.getAmount());
            stock.setBalance(stock.getBalance().add(order.getTotalPrice()));
            walletStock.set(update(stock));
        }, () ->
                walletStock.set(save(WalletStockBuilder.of()
                        .companyCode(order.getCompany().getCode())
                        .amount(order.getAmount())
                        .wallet(order.getWallet())
                        .balance(order.getTotalPrice())
                        .build())));
        return CompletableFuture.completedFuture(walletStock.get());
    }

    private CompletableFuture<WalletStock> processSellOrder (Order order) {
        Optional<WalletStock> walletStock = walletStockRepository.findByCompanyCode(order.getCompany().getCode());
        if (walletStock.isEmpty()) {
            throw new BadRequestException("Stock not found in your wallet");
        } else {
            WalletStock stock = walletStock.get();
            if (stock.getAmount() < order.getAmount()) {
                throw new RuntimeException("Insufficient stock on your wallet");
            }
            stock.setAmount(stock.getAmount() - order.getAmount());
            stock.setBalance(stock.getBalance().min(order.getTotalPrice()));
            return CompletableFuture.completedFuture(update(stock));
        }
    }
}
