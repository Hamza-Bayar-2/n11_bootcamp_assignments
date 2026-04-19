package com.bootcamp.payment_method_implementation.application.service;

import com.bootcamp.payment_method_implementation.application.factory.PaymentStrategyFactory;
import com.bootcamp.payment_method_implementation.application.strategy.payment_strategy.IPaymentStrategy;
import com.bootcamp.payment_method_implementation.common.Result;

import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PaymentStrategyFactory paymentStrategyFactory;

    public PaymentService(PaymentStrategyFactory paymentStrategyFactory) {
        this.paymentStrategyFactory = paymentStrategyFactory;
    }

    public Result<String> processPayment(String method) {
        var strategyResult = paymentStrategyFactory.getStrategy(method);

        if (!strategyResult.isSuccess())
            return Result.failure(strategyResult.errorMessage());

        IPaymentStrategy strategy = strategyResult.data();
        String message = strategy.processPayment();

        return Result.success(message);
    }

    public Result<Set<String>> getAllActivePaymentMethods() {
        Set<String> methods = paymentStrategyFactory.getAllStrategyNames();

        if (methods.isEmpty()) {
            return Result.failure("Aktif ödeme yöntemi bulunamadı.");
        }

        return Result.success(methods);
    }
}
