package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.register;

import com.bootcamp.jwt_and_refresh_token_authentication.common.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@PasswordMatches
public record RegisterCommand(
        @NotBlank 
        String name,

        @NotBlank 
        String surname,

        @NotBlank 
        @Email 
        String email,

        @NotBlank 
        String password,
        
        @NotBlank 
        String passwordConfirm
    ) {

}
