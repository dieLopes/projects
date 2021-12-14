package com.diego.homebroker.repository;

import com.diego.homebroker.domain.Wallet;
import com.diego.homebroker.domain.WalletStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface WalletStockRepository extends JpaRepository<WalletStock, String> {

    Optional<WalletStock> findByCompanyCodeAndWalletId(String code, String walletId);

    List<WalletStock> findByWallet(Wallet wallet);

    @Query("select sum(balance) from WalletStock ws where ws.wallet = :wallet")
    BigDecimal findWalletBalance(Wallet wallet);
}
