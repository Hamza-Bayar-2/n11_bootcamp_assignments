package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

import org.springframework.stereotype.Component;

@Component("visa")
public class VisaPaymentStrategy implements IPaymentStrategy {

    @Override
    public String processPayment() {
        return "Visa ile ödeme işlemi gerçekleştirildi.";
    }

}
