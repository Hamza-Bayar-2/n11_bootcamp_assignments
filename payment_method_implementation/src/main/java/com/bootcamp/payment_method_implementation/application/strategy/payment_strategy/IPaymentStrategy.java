package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

public interface IPaymentStrategy {
    String processPayment(double amount, String payerName);
}
