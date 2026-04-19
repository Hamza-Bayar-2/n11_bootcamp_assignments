package com.bootcamp.payment_method_implementation.api.controller;
import java.util.Set;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bootcamp.payment_method_implementation.application.dto.PaymentRequestDto;
import com.bootcamp.payment_method_implementation.application.service.PaymentService;
import com.bootcamp.payment_method_implementation.common.Result;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // JSON Body ile ödeme yapma
    @PostMapping("/pay")
    public Result<String> pay(@RequestBody PaymentRequestDto requestDto) {
        return paymentService.processPayment(requestDto);
    }

    // Return payment methods
    @GetMapping("/get-all")
    public Result<Set<String>> getAllPaymentMethods() {
        Result<Set<String>> activeMethodsResult = paymentService.getAllActivePaymentMethods();

        return activeMethodsResult;
    }
}
