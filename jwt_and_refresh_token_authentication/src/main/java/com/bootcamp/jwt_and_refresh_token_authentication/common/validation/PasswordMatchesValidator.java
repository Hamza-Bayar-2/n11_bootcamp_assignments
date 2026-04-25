package com.bootcamp.jwt_and_refresh_token_authentication.common.validation;

import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.register.RegisterCommand;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj instanceof RegisterCommand user) {
            return user.password() != null && user.password().equals(user.passwordConfirm());
        }
        return false;
    }
}
