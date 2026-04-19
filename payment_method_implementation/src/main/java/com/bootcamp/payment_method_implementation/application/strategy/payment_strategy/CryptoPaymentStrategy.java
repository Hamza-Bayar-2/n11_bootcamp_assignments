package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

import org.springframework.stereotype.Component;

@Component("crypto")
public class CryptoPaymentStrategy implements IPaymentStrategy {
    @Override
    public String processPayment(double amount, String payerName) {
        return payerName + " tarafından Kripto para ile " + amount + " tutarında ödeme işlemi gerçekleştirildi.";
    }
}