package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

import org.springframework.stereotype.Component;

@Component("visa")
public class VisaPaymentStrategy implements IPaymentStrategy {

    @Override
    public String processPayment(double amount, String payerName) {
        return payerName + " tarafından Visa ile " + amount + " tutarında ödeme işlemi gerçekleştirildi.";
    }

}
