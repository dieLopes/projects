package com.diego.homebroker.repository;

import com.diego.homebroker.domain.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository  extends JpaRepository<Wallet, String> {

    Optional<Wallet> findByUserCpf(String cpf);
}
