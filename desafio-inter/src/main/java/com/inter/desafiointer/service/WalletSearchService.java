package com.inter.desafiointer.service;

import com.inter.desafiointer.builder.WalletBuilder;
import com.inter.desafiointer.domain.Order;
import com.inter.desafiointer.domain.Wallet;
import com.inter.desafiointer.domain.WalletStock;
import com.inter.desafiointer.exception.NotFoundException;
import com.inter.desafiointer.repository.OrderRepository;
import com.inter.desafiointer.repository.WalletRepository;
import com.inter.desafiointer.repository.WalletStockRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WalletSearchService {

    private final WalletRepository walletRepository;
    private final OrderRepository orderRepository;
    private final WalletStockRepository walletStockRepository;

    public WalletSearchService(WalletRepository walletRepository,
                               OrderRepository orderRepository,
                               WalletStockRepository walletStockRepository) {
        this.walletRepository = walletRepository;
        this.orderRepository = orderRepository;
        this.walletStockRepository = walletStockRepository;
    }

    public Wallet findById(String id) {
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Wallet not found"));
        BigDecimal balance = walletStockRepository.findWalletBalance(wallet);
        wallet.setBalance(balance != null ? balance : new BigDecimal("0.00"));
        return wallet;
    }

    public List<Order> findOrders(String id) {
       findById(id);
        return orderRepository.findByWallet(WalletBuilder.of()
                .id(id)
                .build());
    }

    public List<WalletStock> findStocks(String id) {
        findById(id);
        return walletStockRepository.findByWallet(WalletBuilder.of()
                .id(id)
                .build());
    }
}
