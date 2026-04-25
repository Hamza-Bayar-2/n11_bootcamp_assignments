package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.refresh.generate;

public record GenerateRefreshTokenCommand(
        Long userId,
        String oldToken) {
}
