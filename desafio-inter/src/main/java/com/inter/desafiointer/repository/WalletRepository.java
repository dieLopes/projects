package com.inter.desafiointer.repository;

import com.inter.desafiointer.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository  extends JpaRepository<Wallet, String> {
}
