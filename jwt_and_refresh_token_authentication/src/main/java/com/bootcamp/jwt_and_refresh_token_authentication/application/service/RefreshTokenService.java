package com.bootcamp.jwt_and_refresh_token_authentication.application.service;

import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.User;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.UserRefreshToken;

public interface RefreshTokenService {
    String generateRefreshToken(User user, String oldToken);

    UserRefreshToken validateToken(String token);

    void revokeToken(String token);
}
