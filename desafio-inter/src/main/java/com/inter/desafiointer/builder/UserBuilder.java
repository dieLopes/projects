package com.inter.desafiointer.builder;

import com.inter.desafiointer.domain.User;
import com.inter.desafiointer.domain.Wallet;

public class UserBuilder {
    
    private final User user;

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

    public UserBuilder wallet (Wallet wallet) {
        user.setWallet(wallet);
        return this;
    }

    public User build () {
        return user;
    }
}
