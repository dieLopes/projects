package com.example.springbootpostgresql.builder;

import com.example.springbootpostgresql.domain.StatusEnum;
import com.example.springbootpostgresql.domain.User;

public final class UserBuilder {

    private User user;

    private UserBuilder () {
        user = new User();
    }

    public static UserBuilder of () {
        return new UserBuilder();
    }

    public UserBuilder id (String id) {
        user.setId(id);
        return this;
    }

    public UserBuilder name (String name) {
        user.setName(name);
        return this;
    }

    public UserBuilder address (String address) {
        user.setAddress(address);
        return this;
    }

    public UserBuilder integrationId (String integrationId) {
        user.setIntegrationId(integrationId);
        return this;
    }

    public UserBuilder status (StatusEnum status) {
        user.setStatus(status);
        return this;
    }

    public User build () {
        return user;
    }
}
