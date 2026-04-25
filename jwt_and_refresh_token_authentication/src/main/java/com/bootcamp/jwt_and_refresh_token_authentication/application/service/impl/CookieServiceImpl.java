package com.bootcamp.jwt_and_refresh_token_authentication.application.service.impl;

import com.bootcamp.jwt_and_refresh_token_authentication.application.service.CookieService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

@Service
public class CookieServiceImpl implements CookieService {

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    @Value("${jwt.refresh.expiration}")
    private int jwtRefreshExpirationMs;

    @Value("${jwt.cookie.name:jwt_token}")
    private String jwtCookieName;

    @Value("${jwt.refresh.cookie.name:refresh_token}")
    private String jwtRefreshCookieName;

    @Override
    public ResponseCookie generateJwtCookie(String jwtToken) {
        return ResponseCookie.from(jwtCookieName, jwtToken)
                .path("/api")
                .maxAge(jwtExpirationMs / 1000)
                .httpOnly(true)
                .secure(false) // Production aşamasında "true" yapılır
                .sameSite("Strict")
                .build();
    }

    @Override
    public ResponseCookie generateRefreshCookie(String refreshToken) {
        return ResponseCookie.from(jwtRefreshCookieName, refreshToken)
                .path("/api/auth/refresh")
                .maxAge(jwtRefreshExpirationMs / 1000)
                .httpOnly(true)
                .secure(false) // Production aşamasında "true" yapılır
                .sameSite("Strict")
                .build();
    }

    @Override
    public ResponseCookie getCleanJwtCookie() {
        return ResponseCookie.from(jwtCookieName, "")
                .path("/api")
                .maxAge(0)
                .build();
    }

    @Override
    public ResponseCookie getCleanRefreshCookie() {
        return ResponseCookie.from(jwtRefreshCookieName, "")
                .path("/api/auth/refresh")
                .maxAge(0)
                .build();
    }

    @Override
    public String getJwtFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtCookieName);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }

    @Override
    public String getRefreshTokenFromCookies(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, jwtRefreshCookieName);
        if (cookie != null) {
            return cookie.getValue();
        }
        return null;
    }
}