package com.bootcamp.jwt_and_refresh_token_authentication.api.controller;

import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.refresh.generate.GenerateRefreshTokenCommand;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.refresh.generate.GenerateRefreshTokenCommandHandler;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final GenerateRefreshTokenCommandHandler _generateRefreshTokenCommandHandler;

    public AuthController(GenerateRefreshTokenCommandHandler generateRefreshTokenCommandHandler) {
        this._generateRefreshTokenCommandHandler = generateRefreshTokenCommandHandler;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody GenerateRefreshTokenCommand command) {
        // Command'i Handler'a iletiyor ve Result dönüyoruz.
        Result<String> result = _generateRefreshTokenCommandHandler.handle(command);

        if (result.isSuccess()) {
            return ResponseEntity.ok(result.data());
        }

        return ResponseEntity.badRequest().body(result.errorMessage());
    }
}