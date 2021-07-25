package com.diego.taskboard.service;

import com.diego.taskboard.builder.TenantBuilder;
import com.diego.taskboard.builder.UserBuilder;
import com.diego.taskboard.domain.User;
import com.diego.taskboard.exception.NotFoundException;
import com.diego.taskboard.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserSearchServiceTest {

    @InjectMocks
    private UserSearchService userSearchService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void whenFindUserByIdThenReturnUser () throws NotFoundException {
        User user = buildUser("some-id", "Some Name", "Some Address");
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
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
        verify(userRepository).findById(anyString());
    }

    @Test
    public void whenFindUserByIdButIdIsNullThenReturnException () {
        assertThatThrownBy(() -> userSearchService.findById(null))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
        verify(userRepository, never()).findById(anyString());
    }

    @Test
    public void whenFindAllUsersThenReturnUsers () {
        List<User> users = List.of(
                buildUser("some-id", "Some Name", "Some Address"),
                buildUser("another-id", "Another Name", "Another Address"));
        when(userRepository.findAll()).thenReturn(users);
        assertThat(userSearchService.findAll()).hasSize(2)
                .extracting(User::getId,
                        User::getName,
                        User::getAddress)
                .containsExactlyInAnyOrder(
                        tuple("some-id", "Some Name", "Some Address"),
                        tuple("another-id", "Another Name", "Another Address")
                );
        verify(userRepository).findAll();
    }

    private User buildUser(String id, String name, String address) {
        return UserBuilder.of()
                .id(id)
                .name(name)
                .address(address)
                .tenant(TenantBuilder.of()
                        .id("some-tenant-id")
                        .build())
                .build();
    }
}