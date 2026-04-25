package com.bootcamp.jwt_and_refresh_token_authentication.api.controller;

import com.bootcamp.jwt_and_refresh_token_authentication.api.dto.LoginResultDto;
import com.bootcamp.jwt_and_refresh_token_authentication.api.dto.RegisterResultDto;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.refresh.generate.GenerateRefreshTokenCommand;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.refresh.generate.GenerateRefreshTokenCommandHandler;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.login.LoginCommand;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.login.LoginCommandHandler;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.logout.LogoutCommand;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.logout.LogoutCommandHandler;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.register.RegisterCommand;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.register.RegisterCommandHandler;
import com.bootcamp.jwt_and_refresh_token_authentication.application.service.CookieService;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.Result;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final GenerateRefreshTokenCommandHandler _generateRefreshTokenCommandHandler;
    private final RegisterCommandHandler _registerCommandHandler;
    private final LoginCommandHandler _loginCommandHandler;
    private final LogoutCommandHandler _logoutCommandHandler;
    private final CookieService _cookieService;

    public AuthController(
            GenerateRefreshTokenCommandHandler generateRefreshTokenCommandHandler,
            RegisterCommandHandler registerCommandHandler,
            LoginCommandHandler loginCommandHandler,
            LogoutCommandHandler logoutCommandHandler,
            CookieService cookieService) {
        this._generateRefreshTokenCommandHandler = generateRefreshTokenCommandHandler;
        this._registerCommandHandler = registerCommandHandler;
        this._loginCommandHandler = loginCommandHandler;
        this._logoutCommandHandler = logoutCommandHandler;
        this._cookieService = cookieService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterCommand command) {
        Result<RegisterResultDto> result = _registerCommandHandler.handle(command);

        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result.errorMessage());
        }

        RegisterResultDto registerResult = result.data();

        ResponseCookie jwtCookie = _cookieService.generateJwtCookie(registerResult.jwtToken());
        ResponseCookie refreshCookie = _cookieService.generateRefreshCookie(registerResult.refreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body("Registration successful!");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginCommand command) {
        Result<LoginResultDto> result = _loginCommandHandler.handle(command);

        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result.errorMessage());
        }

        LoginResultDto tokens = result.data();

        // Cookie'leri oluşturuyoruz
        ResponseCookie jwtCookie = _cookieService.generateJwtCookie(tokens.jwtToken());
        ResponseCookie refreshCookie = _cookieService.generateRefreshCookie(tokens.refreshToken());

        // Cookie'leri Header'a ekliyoruz
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body("Login successful!");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String refreshToken = _cookieService.getRefreshTokenFromCookies(request);
        LogoutCommand command = new LogoutCommand(refreshToken);
        Result<Void> result = _logoutCommandHandler.handle(command);

        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result.errorMessage());
        }

        ResponseCookie jwtCookie = _cookieService.getCleanJwtCookie();
        ResponseCookie refreshCookie = _cookieService.getCleanRefreshCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body("Logout successful!");
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(HttpServletRequest request) {
        String oldRefreshToken = _cookieService.getRefreshTokenFromCookies(request);
        GenerateRefreshTokenCommand command = new GenerateRefreshTokenCommand(oldRefreshToken);
        Result<LoginResultDto> result = _generateRefreshTokenCommandHandler.handle(command);

        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result.errorMessage());
        }

        LoginResultDto tokens = result.data();

        ResponseCookie jwtCookie = _cookieService.generateJwtCookie(tokens.jwtToken());
        ResponseCookie refreshCookie = _cookieService.generateRefreshCookie(tokens.refreshToken());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body("Token refreshed successfully!");
    }

}