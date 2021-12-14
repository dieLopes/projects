package com.diego.homebroker.service;

import com.diego.homebroker.exception.BadRequestException;
import com.diego.homebroker.builder.UserBuilder;
import com.diego.homebroker.domain.User;
import com.diego.homebroker.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserPersistenceServiceTest {

    @InjectMocks
    private UserPersistenceService userPersistenceService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void whenCreateUserThenSaveUser () {
        User user = buildUser("Some Name");
        when(userRepository.save(eq(user))).thenReturn(user);
        userPersistenceService.save(user);
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void whenCreateUserWithoutNameThenReturnBadRequest () {
        User user = buildUser(null);
        assertThatThrownBy(() ->  userPersistenceService.save(user))
                .isInstanceOf(BadRequestException.class)
                .hasMessage("Name is mandatory");
        verifyNoMoreInteractions(userRepository);
    }

    private User buildUser(String name) {
        return UserBuilder.of()
                .name(name)
                .build();
    }
}
