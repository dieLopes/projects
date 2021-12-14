package com.diego.homebroker.builder;

import com.diego.homebroker.domain.Wallet;
import com.diego.homebroker.domain.WalletStock;

import java.math.BigDecimal;

public class WalletStockBuilder {

    private final WalletStock walletStock;

    private WalletStockBuilder() {
        walletStock = new WalletStock();
    }

    public static WalletStockBuilder of () {
        return new WalletStockBuilder();
    }

    public WalletStockBuilder id (String id) {
        walletStock.setId(id);
        return this;
    }

    public WalletStockBuilder companyCode (String companyCode) {
        walletStock.setCompanyCode(companyCode);
        return this;
    }

    public WalletStockBuilder wallet (Wallet wallet) {
        walletStock.setWallet(wallet);
        return this;
    }

    public WalletStockBuilder amount (int amount) {
        walletStock.setAmount(amount);
        return this;
    }

    public WalletStockBuilder balance (BigDecimal balance) {
        walletStock.setBalance(balance);
        return this;
    }

    public WalletStock build () {
        return walletStock;
    }
}
