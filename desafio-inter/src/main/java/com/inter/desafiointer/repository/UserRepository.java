package com.inter.desafiointer.repository;

import com.inter.desafiointer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByCpf(String cpf);
}
