package com.diego.taskboard.service;

import com.diego.taskboard.domain.Tenant;
import com.diego.taskboard.domain.User;
import com.diego.taskboard.repository.UserRepository;
import com.diego.taskboard.validator.IValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserPersistenceService {

    private final UserRepository userRepository;
    private final UserSearchService userSearchService;
    private final TenantSearchService tenantSearchService;
    private final List<IValidator<User>> validators;

    public UserPersistenceService(UserRepository userRepository,
                                  UserSearchService userSearchService,
                                  TenantSearchService tenantSearchService,
                                  List<IValidator<User>> validators) {
        this.userRepository = userRepository;
        this.userSearchService = userSearchService;
        this.tenantSearchService = tenantSearchService;
        this.validators = validators;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user) {
        Tenant tenant = tenantSearchService.findById(user.getTenant().getId());
        user.setId(UUID.randomUUID().toString());
        user.setTenant(tenant);
        validateUser(user);
        return userRepository.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User update(String id, User user) {
        User oldUser = userSearchService.findById(id);
        validateUser(user);
        user.setId(id);
        user.setTenant(oldUser.getTenant());
        return userRepository.save(user);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(String id) {
        User user = userSearchService.findById(id);
        userRepository.delete(user);
    }

    private void validateUser(User user) {
        validators.forEach(val -> val.validate(user));
    }
}