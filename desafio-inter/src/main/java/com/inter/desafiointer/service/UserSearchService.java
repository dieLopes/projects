package com.inter.desafiointer.service;

import com.inter.desafiointer.domain.User;
import com.inter.desafiointer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserSearchService {

    private final UserRepository userRepository;

    public UserSearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll () {
        return userRepository.findAll();
    }
}
