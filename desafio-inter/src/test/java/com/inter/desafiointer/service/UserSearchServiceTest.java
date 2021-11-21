package com.inter.desafiointer.service;

import com.inter.desafiointer.builder.UserBuilder;
import com.inter.desafiointer.domain.User;
import com.inter.desafiointer.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserSearchServiceTest {

    @InjectMocks
    private UserSearchService userSearchService;
    @Mock
    private UserRepository userRepository;

    @Test
    public void whenFindUsersThenReturnUsers () {
        List<User> users = List.of(
                buildUser("some-id", "Some Name"),
                buildUser("another-id", "Another Name"));
        when(userRepository.findAll()).thenReturn(users);
        assertThat(userSearchService.findAll()).hasSize(2)
                .extracting(User::getId,
                        User::getName)
                .containsExactlyInAnyOrder(
                        tuple("some-id", "Some Name"),
                        tuple("another-id", "Another Name")
                );
        verify(userRepository).findAll();
        verifyNoMoreInteractions(userRepository);
    }

    private User buildUser(String id, String name) {
        return UserBuilder.of()
                .id(id)
                .name(name)
                .build();
    }
}
