package com.example.rabbitproject.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.example.rabbitproject.domain.User;

@Component
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findById(String id);
}
