package com.diego.homebroker.service;

import com.diego.homebroker.exception.NotFoundException;
import com.diego.homebroker.domain.User;
import com.diego.homebroker.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserSearchService {

    private final UserRepository userRepository;

    public UserSearchService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByCpf(String cpf) {
        return userRepository.findByCpf(cpf)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }
}