package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

import org.springframework.stereotype.Component;

@Component("card")
public class CreditCardPaymentStrategy implements IPaymentStrategy {
    @Override
    public String processPayment() {
        return "Kredi Kartı ile ödeme işlemi gerçekleştirildi.";
    }
}