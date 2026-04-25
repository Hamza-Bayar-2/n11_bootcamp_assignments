package com.bootcamp.jwt_and_refresh_token_authentication.api.dto;

public record LoginResultDto(String jwtToken, String refreshToken) {
}