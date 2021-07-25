package com.diego.taskboard.repository;

import com.diego.taskboard.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    List<User> findByNameAndContactsContact(String name, String contact);
}
