package com.diego.taskboard.builder;

import com.diego.taskboard.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserBuilderTest {

    @Test
    public void whenBuildUserThenReturnUser () {
        User user = UserBuilder.of()
                .id("some-id")
                .name("Some Name")
                .address("Some Address")
                .tenant(TenantBuilder.of()
                        .id("some-tenant-id")
                        .build())
                .build();
        assertThat(user.getId()).isEqualTo("some-id");
        assertThat(user.getName()).isEqualTo("Some Name");
        assertThat(user.getAddress()).isEqualTo("Some Address");
        assertThat(user.getTenant().getId()).isEqualTo("some-tenant-id");
    }
}
