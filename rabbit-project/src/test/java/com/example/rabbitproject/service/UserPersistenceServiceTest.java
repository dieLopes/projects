package com.example.rabbitproject.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.rabbitproject.builder.UserBuilder;
import com.example.rabbitproject.domain.User;
import com.example.rabbitproject.exception.UserNotFoundException;
import com.example.rabbitproject.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserPersistenceServiceTest {

    @InjectMocks
    private UserPersistenceService userPersistenceService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserSearchService userSearchService;

    @Test
    public void whenCreateUserThenSaveUser () throws Exception {
        User user = UserBuilder.of()
            .name("Some Name")
            .address("Some Address")
            .build();
        when(userRepository.save(eq(user))).thenReturn(user);
        userPersistenceService.save(user);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void whenCreateUserWithIdThenReturnException () {
        User user = UserBuilder.of()
            .id("some-id")
            .name("Some Name")
            .address("Some Address")
            .build();
        assertThatThrownBy(() -> userPersistenceService.save(user))
            .isInstanceOf(Exception.class)
            .hasMessage("O identificar do usuário não pode estar preenchido");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void whenUpdateUserThenSaveUser () throws Exception {
        User user = UserBuilder.of()
            .id("some-id")
            .name("Some Name")
            .address("Some Address")
            .build();
        when(userSearchService.findById(eq(user.getId()))).thenReturn(user);
        when(userRepository.save(eq(user))).thenReturn(user);
        userPersistenceService.update(user.getId(), user);
        verify(userRepository, times(1)).save(any(User.class));
        verify(userSearchService, times(1)).findById(eq(user.getId()));
    }

    @Test
    public void whenUpdateUserWithoutIdThenReturnException () {
        User user = UserBuilder.of()
            .name("Some Name")
            .address("Some Address")
            .build();
        assertThatThrownBy(() ->  userPersistenceService.update(user.getId(), user))
            .isInstanceOf(Exception.class)
            .hasMessage("O identificar do usuário não pode estar vazio");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void whenUpdateUserNotFoundThenReturnException () {
        User user = UserBuilder.of()
            .id("some-id")
            .name("Some Name")
            .address("Some Address")
            .build();
        when(userSearchService.findById(eq(user.getId())))
            .thenThrow(new UserNotFoundException("Usuário não encontrado com o id: " + user.getId()));
        assertThatThrownBy(() -> userPersistenceService.update(user.getId(), user))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("Usuário não encontrado com o id: " + user.getId());
        verify(userRepository, never()).save(any(User.class));
        verify(userSearchService, times(1)).findById(eq(user.getId()));
    }

    @Test
    public void whenDeleteUserThenRemoveFromDatabase () throws UserNotFoundException {
        User user = UserBuilder.of()
            .id("some-id")
            .name("Some Name")
            .address("Some Address")
            .build();
        when(userSearchService.findById(eq(user.getId()))).thenReturn(user);
        userPersistenceService.delete(user.getId());
        verify(userSearchService).findById(eq(user.getId()));
        verify(userRepository).delete(eq(user));
    }

    @Test
    public void whenDeleteUserButNotFoundThenReturnException () throws UserNotFoundException {
        User user = UserBuilder.of()
            .id("some-id")
            .name("Some Name")
            .address("Some Address")
            .build();
        when(userSearchService.findById(eq(user.getId())))
            .thenThrow(new UserNotFoundException("Usuário não encontrado com o id: " + user.getId()));
        assertThatThrownBy(() ->  userPersistenceService.delete(user.getId()))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("Usuário não encontrado com o id: " + user.getId());
        verify(userSearchService).findById(eq(user.getId()));
        verify(userRepository, never()).delete(eq(user));
    }
}
