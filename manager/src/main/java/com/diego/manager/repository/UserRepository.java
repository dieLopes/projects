package com.diego.manager.repository;

import com.diego.manager.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByNameContainingAndContactsContactContaining(String name, String contact);
}
