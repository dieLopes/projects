package com.inter.desafiointer.repository;

import com.inter.desafiointer.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;

public interface WalletRepository  extends JpaRepository<Wallet, String> {

}
