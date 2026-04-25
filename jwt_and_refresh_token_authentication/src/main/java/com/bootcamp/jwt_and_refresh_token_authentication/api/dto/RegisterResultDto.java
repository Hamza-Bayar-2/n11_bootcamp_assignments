package com.bootcamp.jwt_and_refresh_token_authentication.api.dto;

import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.User;

public record RegisterResultDto(User user, String jwtToken, String refreshToken) {
}
