package com.diego.manager.service;

import com.diego.manager.builder.TenantBuilder;
import com.diego.manager.builder.UserBuilder;
import com.diego.manager.domain.Tenant;
import com.diego.manager.domain.User;
import com.diego.manager.exception.NotFoundException;
import com.diego.manager.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserPersistenceServiceTest {

    @InjectMocks
    private UserPersistenceService userPersistenceService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserSearchService userSearchService;
    @Mock
    private TenantSearchService tenantSearchService;

    @Test
    public void whenCreateUserThenSaveUser () {
        User user = buildUser();
        user.setId(null);
        when(tenantSearchService.findById(eq("some-tenant-id"))).thenReturn(new Tenant());
        when(userRepository.save(eq(user))).thenReturn(user);
        userPersistenceService.save(user);
        verify(userRepository).save(any(User.class));
        verify(tenantSearchService).findById(eq("some-tenant-id"));
    }

    @Test
    public void whenUpdateUserThenSaveUser () {
        User user = buildUser();
        when(userSearchService.findById(eq(user.getId()))).thenReturn(user);
        when(userRepository.save(eq(user))).thenReturn(user);
        userPersistenceService.update(user.getId(), user);
        verify(userRepository).save(any(User.class));
        verify(userSearchService).findById(eq(user.getId()));
    }

    @Test
    public void whenUpdateUserWithoutIdThenReturnException () {
        User user = buildUser();
        user.setId(null);
        when(userSearchService.findById(eq(null)))
                .thenThrow(new NotFoundException("User not found"));
        assertThatThrownBy(() ->  userPersistenceService.update(user.getId(), user))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
        verify(userSearchService).findById(eq(user.getId()));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void whenUpdateUserNotFoundThenReturnException () {
        User user = buildUser();
        when(userSearchService.findById(eq(user.getId())))
                .thenThrow(new NotFoundException("User not found"));
        assertThatThrownBy(() -> userPersistenceService.update(user.getId(), user))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
        verify(userSearchService).findById(eq(user.getId()));
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void whenDeleteUserThenRemoveFromDatabase () throws NotFoundException {
        User user = buildUser();
        when(userSearchService.findById(eq(user.getId()))).thenReturn(user);
        userPersistenceService.delete(user.getId());
        verify(userSearchService).findById(eq(user.getId()));
        verify(userRepository).delete(eq(user));
    }

    @Test
    public void whenDeleteUserButNotFoundThenReturnException () throws NotFoundException {
        User user = buildUser();
        when(userSearchService.findById(eq(user.getId())))
                .thenThrow(new NotFoundException("User not found"));
        assertThatThrownBy(() ->  userPersistenceService.delete(user.getId()))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("User not found");
        verify(userSearchService).findById(eq(user.getId()));
        verify(userRepository, never()).delete(eq(user));
    }

    private User buildUser () {
        return UserBuilder.of()
                .id("some-id")
                .name("Some Name")
                .address("Some Address")
                .tenant(TenantBuilder.of()
                        .id("some-tenant-id")
                        .build())
                .build();
    }
}
