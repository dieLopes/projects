package com.example.rabbitproject.repository;

import com.example.rabbitproject.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public interface UserRepository extends CrudRepository<User, String> {

    Optional<User> findById(String id);
}
