package com.diego.manager.builder;

import com.diego.manager.domain.User;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
