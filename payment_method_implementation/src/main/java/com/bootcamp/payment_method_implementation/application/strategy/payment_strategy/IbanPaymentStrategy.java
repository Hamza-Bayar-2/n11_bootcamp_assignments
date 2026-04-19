package com.bootcamp.payment_method_implementation.application.strategy.payment_strategy;

import org.springframework.stereotype.Component;

@Component("iban")
public class IbanPaymentStrategy implements IPaymentStrategy {

    @Override
    public String processPayment(double amount, String payerName) {
        return payerName + " tarafından Iban ile " + amount + " tutarında ödeme işlemi gerçekleştirildi.";
    }

}
