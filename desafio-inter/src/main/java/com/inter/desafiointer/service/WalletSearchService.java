package com.inter.desafiointer.service;

import com.inter.desafiointer.builder.WalletBuilder;
import com.inter.desafiointer.domain.Order;
import com.inter.desafiointer.domain.User;
import com.inter.desafiointer.domain.Wallet;
import com.inter.desafiointer.domain.WalletStock;
import com.inter.desafiointer.exception.NotFoundException;
import com.inter.desafiointer.repository.OrderRepository;
import com.inter.desafiointer.repository.UserRepository;
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
    private final UserRepository userRepository;

    public WalletSearchService(WalletRepository walletRepository,
                               OrderRepository orderRepository,
                               WalletStockRepository walletStockRepository,
                               UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.orderRepository = orderRepository;
        this.walletStockRepository = walletStockRepository;
        this.userRepository = userRepository;
    }

    public Wallet findByUserCpf(String cpf) {
        User user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new NotFoundException("Wallet not found"));
        Wallet wallet = user.getWallet();
        BigDecimal balance = walletStockRepository.findWalletBalance(wallet);
        wallet.setBalance(balance != null ? balance : new BigDecimal("0.00"));
        return wallet;
    }

    public List<Order> findOrders(String cpf) {
        User user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return orderRepository.findByWallet(user.getWallet());
    }

    public List<WalletStock> findStocks(String cpf) {
        User user = userRepository.findByCpf(cpf)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return walletStockRepository.findByWallet(user.getWallet());
    }
}
