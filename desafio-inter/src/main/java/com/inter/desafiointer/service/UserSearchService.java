package com.inter.desafiointer.service;

import com.inter.desafiointer.domain.User;
import com.inter.desafiointer.exception.NotFoundException;
import com.inter.desafiointer.repository.UserRepository;
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