package com.inter.desafiointer.service;

import com.inter.desafiointer.builder.UserBuilder;
import com.inter.desafiointer.domain.User;
import com.inter.desafiointer.exception.NotFoundException;
import com.inter.desafiointer.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
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
    public void whenFindUserByCpfThenReturnUser () {
        User user = buildUser("some-id", "Some Name");
        when(userRepository.findByCpf(eq("11111111111"))).thenReturn(Optional.of(user));
        assertThat(userSearchService.findByCpf("11111111111")).satisfies(findComp -> {
            assertThat(findComp.getId()).isEqualTo(user.getId());
            assertThat(findComp.getName()).isEqualTo(user.getName());
            assertThat(findComp.getCpf()).isEqualTo(user.getCpf());
        });
        verify(userRepository).findByCpf(eq("11111111111"));
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void whenFindUserByInvalidCpfThenReturnNotFound () {
        when(userRepository.findByCpf(anyString())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userSearchService.findByCpf("222"))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
        verify(userRepository).findByCpf(eq("222"));
        verifyNoMoreInteractions(userRepository);
    }

    private User buildUser(String id, String name) {
        return UserBuilder.of()
                .id(id)
                .cpf("11111111111")
                .name(name)
                .build();
    }
}
