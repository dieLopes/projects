package com.example.springbootmongodb.repository;

import com.example.springbootmongodb.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findById(String id);
}
