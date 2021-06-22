package com.example.rabbitproject.service;

import com.example.rabbitproject.builder.UserBuilder;
import com.example.rabbitproject.domain.User;
import com.example.rabbitproject.exception.UserNotFoundException;
import com.example.rabbitproject.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserSearchServiceTest {

    @InjectMocks
    private UserSearchService userSearchService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void whenFindUserByIdThenReturnUser () throws UserNotFoundException {
        User user = UserBuilder.of()
            .id("some-id")
            .name("Some Name")
            .address("Some Address")
            .build();
        when(userRepository.findById(eq(user.getId()))).thenReturn(Optional.of(user));
        assertThat(userSearchService.findById(user.getId())).satisfies(findUser -> {
            assertThat(findUser.getId()).isEqualTo(user.getId());
            assertThat(findUser.getName()).isEqualTo(user.getName());
            assertThat(findUser.getAddress()).isEqualTo(user.getAddress());
        });
        verify(userRepository).findById(eq(user.getId()));
    }

    @Test
    public void whenFindUserByIdButNotFoundThenReturnException () {
        when(userRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userSearchService.findById("some-id"))
            .isInstanceOf(UserNotFoundException.class)
            .hasMessage("User with id some-id not found");
        verify(userRepository).findById(anyString());
    }

    @Test
    public void whenFindAllUsersThenReturnUsers () {
        Iterable<User> users = List.of(
            UserBuilder.of()
                .id("some-id")
                .name("Some Name")
                .address("Some Address")
                .integrationId("some-integration-id")
                .build(),
            UserBuilder.of()
                .id("another-id")
                .name("Another Name")
                .address("Another Address")
                .integrationId("another-integration-id")
                .build());
        when(userRepository.findAll()).thenReturn(users);
        assertThat(userSearchService.findAll()).hasSize(2)
            .extracting(User::getId,
                User::getName,
                User::getAddress,
                User::getIntegrationId)
            .containsExactlyInAnyOrder(
                tuple("some-id", "Some Name", "Some Address", "some-integration-id"),
                tuple("another-id", "Another Name", "Another Address", "another-integration-id")
            );
        verify(userRepository).findAll();
    }
}
