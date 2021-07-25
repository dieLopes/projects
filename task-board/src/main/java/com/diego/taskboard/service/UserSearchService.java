package com.diego.taskboard.service;

import com.diego.taskboard.domain.User;
import com.diego.taskboard.exception.NotFoundException;
import com.diego.taskboard.repository.UserRepository;
import com.diego.taskboard.repository.UserRepositoryCustom;
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
    public List<User> findByNameAndContact(String name, String email) {
        return userRepositoryCustom.findByFilters(name, email);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
