package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

import org.springframework.stereotype.Component;

@Component("paypal")
public class PayPalPaymentStrategy implements IPaymentStrategy {
    @Override
    public String processPayment() {
        return "PayPal ile ödeme işlemi gerçekleştirildi.";
    }
}