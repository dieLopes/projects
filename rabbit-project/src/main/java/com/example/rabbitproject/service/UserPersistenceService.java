package com.example.rabbitproject.service;

import com.example.rabbitproject.exception.UserNotFoundException;

import org.springframework.stereotype.Component;

import com.example.rabbitproject.domain.User;
import com.example.rabbitproject.repository.UserRepository;

import javax.validation.Valid;

import java.util.UUID;

@Component
public class UserPersistenceService {

    private UserRepository userRepository;
    private UserSearchService userSearchService;

    public UserPersistenceService(UserRepository userRepository,
        UserSearchService userSearchService) {
        this.userRepository = userRepository;
        this.userSearchService = userSearchService;
    }

    public User save (@Valid User user) throws Exception {
        if (user.getId() != null) {
            throw new Exception("O identificar do usuário não pode estar preenchido");
        }
        user.setId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    public User update (String id, @Valid User user) throws Exception {
        if (id == null) {
            throw new Exception("O identificar do usuário não pode estar vazio");
        }
        User retrieveUser = userSearchService.findById(id);
        retrieveUser.setName(user.getName());
        retrieveUser.setAddress(user.getAddress());
        return userRepository.save(retrieveUser);
    }

    public void delete (String id) throws UserNotFoundException {
        User user = userSearchService.findById(id);
        userRepository.delete(user);
    }
}
