package com.example.rabbitproject.builder;

import com.example.rabbitproject.domain.User;

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

    public User build () {
        return user;
    }
}
