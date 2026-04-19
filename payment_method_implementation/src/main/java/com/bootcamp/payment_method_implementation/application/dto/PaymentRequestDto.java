package com.bootcamp.payment_method_implementation.application.dto;

public record PaymentRequestDto(
    double amount,
    String payerName,
    String method,
    String description
) {
}
