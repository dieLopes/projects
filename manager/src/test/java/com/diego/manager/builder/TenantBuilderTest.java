package com.diego.manager.builder;

import com.diego.manager.domain.Tenant;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
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
