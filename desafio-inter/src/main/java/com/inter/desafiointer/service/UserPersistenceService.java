package com.inter.desafiointer.service;

import com.inter.desafiointer.builder.WalletBuilder;
import com.inter.desafiointer.domain.User;
import com.inter.desafiointer.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserPersistenceService {

    private final UserRepository userRepository;

    public UserPersistenceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save (User user) {
        user.setId(UUID.randomUUID().toString());
        user.setWallet(WalletBuilder.of()
                .id(UUID.randomUUID().toString())
                .build());
        return userRepository.save(user);
    }
}