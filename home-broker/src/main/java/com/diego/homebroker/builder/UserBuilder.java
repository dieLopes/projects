package com.diego.homebroker.builder;

import com.diego.homebroker.domain.User;
import com.diego.homebroker.domain.Wallet;

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

    public UserBuilder cpf (String cpf) {
        user.setCpf(cpf);
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
