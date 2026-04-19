package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

import org.springframework.stereotype.Component;

@Component("paypal")
public class PayPalPaymentStrategy implements IPaymentStrategy {
    @Override
    public String processPayment(double amount, String payerName) {
        return payerName + " tarafından PayPal ile " + amount + " tutarında ödeme işlemi gerçekleştirildi.";
    }
}