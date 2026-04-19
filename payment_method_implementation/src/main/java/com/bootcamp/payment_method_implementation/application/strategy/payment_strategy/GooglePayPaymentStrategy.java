package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

import org.springframework.stereotype.Component;

@Component("google")
public class GooglePayPaymentStrategy implements IPaymentStrategy {
    @Override
    public String processPayment(double amount, String payerName) {
        return payerName + " tarafından Google Pay ile " + amount + " tutarında ödeme işlemi gerçekleştirildi.";
    }
}