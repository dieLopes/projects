package com.inter.desafiointer.service;

import com.inter.desafiointer.domain.Wallet;
import com.inter.desafiointer.repository.WalletRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletPersistenceService {

    private final WalletRepository walletRepository;

    public WalletPersistenceService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet save (Wallet wallet) {
        wallet.setId(UUID.randomUUID().toString());
        return walletRepository.save(wallet);
    }
}
