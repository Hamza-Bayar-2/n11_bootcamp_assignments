package com.bootcamp.jwt_and_refresh_token_authentication.application.service;

import java.util.List;

public interface AccessTokenService {
    String generateToken(String username, List<String> roles);

    List<String> getUserRolesFromToken(String token);

    String getUsernameFromToken(String token);

    boolean validateToken(String token);

    boolean isExpired(String token);
}
