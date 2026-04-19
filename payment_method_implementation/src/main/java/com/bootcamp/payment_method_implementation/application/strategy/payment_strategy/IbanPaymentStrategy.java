package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

import org.springframework.stereotype.Component;

@Component("iban")
public class IbanPaymentStrategy implements IPaymentStrategy {

    @Override
    public String processPayment() {
        return "Iban ile ödeme işlemi gerçekleştirildi.";
    }

}
