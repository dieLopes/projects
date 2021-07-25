package com.strategy;

public interface PayStrategy {

    boolean pay(int paymentAmount);
    void collectPaymentDetails();
}
