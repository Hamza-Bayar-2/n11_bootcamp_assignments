package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

import org.springframework.stereotype.Component;

@Component("google")
public class GooglePayPaymentStrategy implements IPaymentStrategy {
    @Override
    public String processPayment() {
        return "Google Pay ile ödeme işlemi gerçekleştirildi.";
    }
}