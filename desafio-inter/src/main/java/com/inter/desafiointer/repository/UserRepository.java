package com.inter.desafiointer.repository;

import com.inter.desafiointer.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
