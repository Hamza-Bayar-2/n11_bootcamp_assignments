package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

import org.springframework.stereotype.Component;

@Component("crypto")
public class CryptoPaymentStrategy implements IPaymentStrategy {
    @Override
    public String processPayment() {
        return "Kripto para ile ödeme işlemi gerçekleştirildi.";
    }
}