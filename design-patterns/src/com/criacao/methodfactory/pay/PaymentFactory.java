package com.criacao.methodfactory.pay;

import static com.criacao.methodfactory.pay.PaymentType.CREDIT_CARD;
import static com.criacao.methodfactory.pay.PaymentType.PIC_PAY;

public class PaymentFactory {

    public Payment getPayment (PaymentType type) {
        if (CREDIT_CARD.equals(type)) {
            return new Card();
        } else if (PIC_PAY.equals(type)) {
            return new PicPay();
        }
        throw new IllegalArgumentException("O tipo informado não é valido");
    }
}
