package com.inter.desafiointer.service;

import com.inter.desafiointer.builder.UserBuilder;
import com.inter.desafiointer.domain.User;
import com.inter.desafiointer.exception.BadRequestException;
import com.inter.desafiointer.repository.UserRepository;
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
    public void whenCreateCompanyThenSaveCompany () {
        User user = buildUser("Some Name");
        when(userRepository.save(eq(user))).thenReturn(user);
        userPersistenceService.save(user);
        verify(userRepository).save(any(User.class));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void whenCreateCompanyWithoutNameThenReturnBadRequest () {
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
