package com.diego.homebroker.builder;

import com.diego.homebroker.domain.User;
import com.diego.homebroker.domain.Wallet;

import java.math.BigDecimal;

public class WalletBuilder {

    private final Wallet wallet;

    private WalletBuilder() {
        wallet = new Wallet();
    }

    public static WalletBuilder of () {
        return new WalletBuilder();
    }

    public WalletBuilder id (String id) {
        wallet.setId(id);
        return this;
    }

    public WalletBuilder balance (BigDecimal balance) {
        wallet.setBalance(balance);
        return this;
    }

    public WalletBuilder user (User user) {
        wallet.setUser(user);
        return this;
    }

    public Wallet build () {
        return wallet;
    }
}
