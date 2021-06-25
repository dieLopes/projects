package com.example.springbootmongodb.builder;

import com.example.springbootmongodb.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static com.example.springbootmongodb.domain.StatusEnum.ACTIVE;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UserBuilderTest {

    @Test
    public void whenBuildUserThenReturnUser () {
        User user = UserBuilder.of()
                .id("some-id")
                .name("Some Name")
                .address("Some Address")
                .status(ACTIVE)
                .build();
        assertThat(user.getId()).isEqualTo("some-id");
        assertThat(user.getName()).isEqualTo("Some Name");
        assertThat(user.getAddress()).isEqualTo("Some Address");
        assertThat(user.getIntegrationId()).isEqualTo("some-integration-id");
        assertThat(user.getStatus()).isEqualTo(ACTIVE);
    }
}
