package com.example.springbootmongodb.builder;

import com.example.springbootmongodb.domain.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeBuilderTest {

    @Test
    public void whenBuildUserThenReturnUser () {
        Employee employee = EmployeeBuilder.of()
                .id("some-id")
                .name("Some Name")
                .address("Some Address")
                .tenant(TenantBuilder.of()
                        .id("some-tenant-id")
                        .build())
                .build();
        assertThat(employee.getId()).isEqualTo("some-id");
        assertThat(employee.getName()).isEqualTo("Some Name");
        assertThat(employee.getAddress()).isEqualTo("Some Address");
        assertThat(employee.getTenant().getId()).isEqualTo("some-tenant-id");
    }
}
