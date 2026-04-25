package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.refresh.revoke;

public record RevokeRefreshTokenCommand(String token) {
}