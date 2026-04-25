package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.deletebyemail;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record DeleteUserByEmailCommand(
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,
        
        String currentSessionEmail,
        
        boolean isAdmin) {
}
