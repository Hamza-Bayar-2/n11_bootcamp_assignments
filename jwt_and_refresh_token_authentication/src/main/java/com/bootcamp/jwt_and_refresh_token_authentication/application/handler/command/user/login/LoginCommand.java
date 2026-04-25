package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginCommand(
        @NotBlank 
        @Email 
        String email,

        @NotBlank 
        String password
    ) {
}