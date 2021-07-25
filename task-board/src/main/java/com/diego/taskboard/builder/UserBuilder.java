package com.diego.taskboard.builder;

import com.diego.taskboard.domain.Contact;
import com.diego.taskboard.domain.User;
import com.diego.taskboard.domain.Tenant;

import java.util.Set;

public final class UserBuilder {

    private User user;

    private UserBuilder() {
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

    public UserBuilder contacts (Set<Contact> contacts) {
        user.setContacts(contacts);
        return this;
    }

    public UserBuilder tenant (Tenant tenant) {
        user.setTenant(tenant);
        return this;
    }

    public User build () {
        return user;
    }
}
