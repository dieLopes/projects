package com.diego.manager.builder;

import com.diego.manager.domain.Tenant;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TenantBuilderTest {

    @Test
    public void whenBuildUserThenReturnUser () {
        Tenant tenant = TenantBuilder.of()
                .id("some-id")
                .name("Some Name")
                .build();
        assertThat(tenant.getId()).isEqualTo("some-id");
        assertThat(tenant.getName()).isEqualTo("Some Name");
    }
}
