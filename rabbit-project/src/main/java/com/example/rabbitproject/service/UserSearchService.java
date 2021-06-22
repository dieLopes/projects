package com.example.rabbitproject.service;

import com.example.rabbitproject.domain.User;
import com.example.rabbitproject.exception.UserNotFoundException;
import com.example.rabbitproject.repository.UserRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class UserSearchService {

    private final UserRepository userRepository;

    public UserSearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public User findById (String id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
            new UserNotFoundException("User with id %s not found", id));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED, readOnly = true)
    public List<User> findAll () {
        return StreamSupport
            .stream(userRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }
}
