package com.diego.manager.service;

import com.diego.manager.repository.UserRepository;
import com.diego.manager.repository.UserRepositoryCustom;
import com.diego.manager.domain.User;
import com.diego.manager.exception.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserSearchService {

    private final UserRepository userRepository;
    private final UserRepositoryCustom userRepositoryCustom;

    public UserSearchService(UserRepository userRepository,
                             UserRepositoryCustom userRepositoryCustom) {
        this.userRepository = userRepository;
        this.userRepositoryCustom = userRepositoryCustom;
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public User findById(String id) {
        if (id == null) {
            throw new NotFoundException("User not found");
        }
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<User> findByNameAndContact(String name, String contact) {
        return userRepository.findByNameContainingAndContactsContactContaining(name, contact);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
