package com.criacao.methodfactory.pay;

public class PayDemo {

    public static void main(String[] args) {
        new PaymentFactory().getPayment(PaymentType.CREDIT_CARD);
        new PaymentFactory().getPayment(PaymentType.PIC_PAY);
    }
}
