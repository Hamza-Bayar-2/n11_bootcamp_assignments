package com.bootcamp.jwt_and_refresh_token_authentication.application.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseCookie;

public interface CookieService {
    ResponseCookie generateJwtCookie(String jwtToken);

    ResponseCookie generateRefreshCookie(String refreshToken);

    ResponseCookie getCleanJwtCookie();

    ResponseCookie getCleanRefreshCookie();

    String getJwtFromCookies(HttpServletRequest request);

    String getRefreshTokenFromCookies(HttpServletRequest request);
}