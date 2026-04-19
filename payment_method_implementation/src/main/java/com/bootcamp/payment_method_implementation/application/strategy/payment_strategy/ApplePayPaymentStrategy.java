package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

import org.springframework.stereotype.Component;

@Component("apple")
public class ApplePayPaymentStrategy implements IPaymentStrategy {
    @Override
    public String processPayment() {
        return "Apple Pay ile ödeme işlemi gerçekleştirildi.";
    }
}