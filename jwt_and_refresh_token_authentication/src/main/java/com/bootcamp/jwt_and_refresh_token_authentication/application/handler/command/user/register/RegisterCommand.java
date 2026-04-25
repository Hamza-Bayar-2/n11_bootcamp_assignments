package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.register;

import com.bootcamp.jwt_and_refresh_token_authentication.common.validation.PasswordMatches;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@PasswordMatches
public record RegisterCommand(
        @NotBlank(message = "Name is required") 
        String name,

        @NotBlank(message = "Surname is required") 
        String surname,

        @NotBlank(message = "Email is required") 
        @Email(message = "Invalid email format") 
        String email,

        @NotBlank(message = "Password is required") 
        String password,
        
        @NotBlank(message = "Password confirmation is required") 
        String passwordConfirm
    ) {

}
