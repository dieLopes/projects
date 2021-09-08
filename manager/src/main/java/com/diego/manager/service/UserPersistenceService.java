package com.diego.manager.service;

import com.diego.manager.exception.BadRequestException;
import com.diego.manager.exception.NotFoundException;
import com.diego.manager.repository.UserRepository;
import com.diego.manager.domain.Tenant;
import com.diego.manager.domain.User;
import com.diego.manager.validator.IValidator;
import com.diego.manager.validator.user.UserAddressValidator;
import com.diego.manager.validator.user.UserNameValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserPersistenceService {

    private final UserRepository userRepository;
    private final UserSearchService userSearchService;
    private final TenantSearchService tenantSearchService;
    private static final List<IValidator<User>> validations = new ArrayList<>();

    static {
        validations.add(new UserAddressValidator());
        validations.add(new UserNameValidator());
    }

    public UserPersistenceService(UserRepository userRepository,
                                  UserSearchService userSearchService,
                                  TenantSearchService tenantSearchService) {
        this.userRepository = userRepository;
        this.userSearchService = userSearchService;
        this.tenantSearchService = tenantSearchService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user) {
        Tenant tenant;
        try {
            tenant = tenantSearchService.findById(user.getTenant().getId());
        } catch (NotFoundException e) {
            throw new BadRequestException("Tenant not found");
        }
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
        validations.forEach(val -> val.validate(user));
    }
}