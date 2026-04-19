package com.bootcamp.payment_method_implementation.application.factory;

import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.bootcamp.payment_method_implementation.application.strategy.payment_strategy.IPaymentStrategy;
import com.bootcamp.payment_method_implementation.common.Result;

@Component
public class PaymentStrategyFactory {
    private final Map<String, IPaymentStrategy> strategies;

    public PaymentStrategyFactory(Map<String, IPaymentStrategy> strategies) {
        this.strategies = strategies;
    }

    public Result<IPaymentStrategy> getStrategy(String strategy) {

        // Annotasyonda verdiğimiz isimlerle (visa, paypal, card vb.) eşleştirme
        // yapıyoruz.
        IPaymentStrategy paymentStrategy = strategies.get(strategy.toLowerCase());

        if (paymentStrategy == null)
            return Result.failure("Ödeme yöntemi bulunamadı: " + strategy);

        return Result.success(paymentStrategy);
    }

    public Set<String> getAllStrategyNames() {
        return strategies.keySet();
    }
}
