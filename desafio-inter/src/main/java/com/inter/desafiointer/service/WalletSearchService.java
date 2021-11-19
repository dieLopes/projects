package com.inter.desafiointer.service;

import com.inter.desafiointer.builder.WalletBuilder;
import com.inter.desafiointer.domain.Order;
import com.inter.desafiointer.domain.User;
import com.inter.desafiointer.domain.Wallet;
import com.inter.desafiointer.exception.NotFoundException;
import com.inter.desafiointer.repository.OrderRepository;
import com.inter.desafiointer.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WalletSearchService {

    private final WalletRepository walletRepository;
    private final OrderRepository orderRepository;

    public WalletSearchService(WalletRepository walletRepository,
                               OrderRepository orderRepository) {
        this.walletRepository = walletRepository;
        this.orderRepository = orderRepository;
    }

    public List<Order> findOrders(String id) {
        walletRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Wallet not found"));
        return orderRepository.findByWallet(WalletBuilder.of()
                .id(id)
                .build());
    }
}
