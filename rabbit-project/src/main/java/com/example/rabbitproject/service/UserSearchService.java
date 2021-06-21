package com.example.rabbitproject.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Component;

import com.example.rabbitproject.domain.User;
import com.example.rabbitproject.exception.UserNotFoundException;
import com.example.rabbitproject.repository.UserRepository;

@Component
public class UserSearchService {

    private UserRepository userRepository;

    public UserSearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findById (String id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(() ->
            new UserNotFoundException("Usuário não encontrado com o id: " + id));
    }

    public List<User> findAll () {
        return StreamSupport
            .stream(userRepository.findAll().spliterator(), false)
            .collect(Collectors.toList());
    }
}
