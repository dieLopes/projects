package com.diego.homebroker.service;

import com.diego.homebroker.domain.WalletStock;
import com.diego.homebroker.exception.NotFoundException;
import com.diego.homebroker.repository.OrderRepository;
import com.diego.homebroker.repository.WalletRepository;
import com.diego.homebroker.repository.WalletStockRepository;
import com.diego.homebroker.domain.Order;
import com.diego.homebroker.domain.User;
import com.diego.homebroker.domain.Wallet;
import com.diego.homebroker.repository.UserRepository;
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
