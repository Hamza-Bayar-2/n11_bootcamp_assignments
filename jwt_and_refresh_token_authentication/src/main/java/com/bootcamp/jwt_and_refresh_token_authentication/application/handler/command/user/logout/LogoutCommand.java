package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.logout;

import jakarta.validation.constraints.NotBlank;

public record LogoutCommand(
    @NotBlank
    String refreshToken
) {
}
