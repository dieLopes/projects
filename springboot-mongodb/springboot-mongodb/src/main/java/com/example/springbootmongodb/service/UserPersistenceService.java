package com.example.springbootmongodb.service;

import com.example.springbootmongodb.repository.UserRepository;
import com.example.springbootmongodb.domain.User;
import com.example.springbootmongodb.exception.UserNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.UUID;

import static com.example.springbootmongodb.domain.StatusEnum.ACTIVE;

@Component
public class UserPersistenceService {

    private UserRepository userRepository;
    private UserSearchService userSearchService;

    public UserPersistenceService(UserRepository userRepository,
        UserSearchService userSearchService) {
        this.userRepository = userRepository;
        this.userSearchService = userSearchService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User save (@Valid User user) throws Exception {
        if (user.getId() != null) {
            throw new Exception("User id must be null");
        }
        user.setStatus(ACTIVE);
        user.setId(UUID.randomUUID().toString());
        return userRepository.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User update (String id, @Valid User user) throws Exception {
        if (id == null) {
            throw new Exception("User id cannot be null");
        }
        User retrieveUser = userSearchService.findById(id);
        retrieveUser.setName(user.getName());
        retrieveUser.setAddress(user.getAddress());
        return userRepository.save(retrieveUser);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete (String id) throws UserNotFoundException {
        User user = userSearchService.findById(id);
        userRepository.delete(user);
    }
}
