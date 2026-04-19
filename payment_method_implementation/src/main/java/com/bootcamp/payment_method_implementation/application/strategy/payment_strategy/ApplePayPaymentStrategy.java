package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

import org.springframework.stereotype.Component;

@Component("apple")
public class ApplePayPaymentStrategy implements IPaymentStrategy {
    @Override
    public String processPayment(double amount, String payerName) {
        return payerName + " tarafından Apple Pay ile " + amount + " tutarında ödeme işlemi gerçekleştirildi.";
    }
}