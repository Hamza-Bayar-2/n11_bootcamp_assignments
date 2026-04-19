package com.bootcamp.payment_method_implementation.api.controller;

import java.util.Set;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bootcamp.payment_method_implementation.application.service.PaymentService;
import com.bootcamp.payment_method_implementation.common.Result;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    final private PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // Pay with desired payment method
    @GetMapping("/pay/{method}")
    public Result<String> getPaymentMethod(@PathVariable String method) {
        Result<String> paymentResult = paymentService.processPayment(method);

        return paymentResult;
    }

    // Return payment methods
    @GetMapping("/get-all")
    public Result<Set<String>> getAllPaymentMethods() {
        Result<Set<String>> activeMethodsResult = paymentService.getAllActivePaymentMethods();

        return activeMethodsResult;
    }
}
