package com.example.rabbitproject.builder;

import static com.example.rabbitproject.domain.StatusEnum.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.rabbitproject.domain.User;

@RunWith(MockitoJUnitRunner.class)
public class UserBuilderTest {

    @Test
    public void whenBuildUserThenReturnUser () {
        User user = UserBuilder.of()
                .id("some-id")
                .name("Some Name")
                .address("Some Address")
                .integrationId("some-integration-id")
                .status(ACTIVE)
                .build();
        assertThat(user.getId()).isEqualTo("some-id");
        assertThat(user.getName()).isEqualTo("Some Name");
        assertThat(user.getAddress()).isEqualTo("Some Address");
        assertThat(user.getIntegrationId()).isEqualTo("some-integration-id");
        assertThat(user.getStatus()).isEqualTo(ACTIVE);
    }
}
