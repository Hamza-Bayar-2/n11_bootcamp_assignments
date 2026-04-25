package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.refresh.generate;

import jakarta.validation.constraints.NotBlank;

public record GenerateRefreshTokenCommand(
        @NotBlank
        String oldToken) {
}
