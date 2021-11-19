package com.inter.desafiointer.repository;

import com.inter.desafiointer.domain.Wallet;
import com.inter.desafiointer.domain.WalletStock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletStockRepository extends JpaRepository<WalletStock, String> {

    Optional<WalletStock> findByCompanyCode(String code);

    List<WalletStock> findByWallet(Wallet wallet);
}
