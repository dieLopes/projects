package com.diego.homebroker.service;

import com.diego.homebroker.builder.WalletBuilder;
import com.diego.homebroker.exception.BadRequestException;
import com.diego.homebroker.domain.User;
import com.diego.homebroker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserPersistenceService {

    private final UserRepository userRepository;

    public UserPersistenceService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User save (User user) {
        validateFields(user);
        user.setId(UUID.randomUUID().toString());
        user.setWallet(WalletBuilder.of()
                .id(UUID.randomUUID().toString())
                .build());
        return userRepository.save(user);
    }

    private void validateFields (User user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new BadRequestException("Name is mandatory");
        }
    }
}
