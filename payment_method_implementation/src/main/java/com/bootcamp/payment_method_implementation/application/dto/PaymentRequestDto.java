package com.bootcamp.payment_method_implementation.application.dto;

import io.micrometer.common.lang.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PaymentRequestDto(

    @Positive(message = "Amount must be greater than 0")
    double amount,

    @NotBlank(message = "Payer name is required")
    String payerName,

    @NotBlank(message = "Payment method is required")
    String method,

    @Nullable
    String description
) {
}
