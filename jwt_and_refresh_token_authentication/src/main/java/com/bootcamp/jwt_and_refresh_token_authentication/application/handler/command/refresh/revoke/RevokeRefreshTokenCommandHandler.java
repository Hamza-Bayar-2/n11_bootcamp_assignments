package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.refresh.revoke;

import org.springframework.stereotype.Component;

import com.bootcamp.jwt_and_refresh_token_authentication.application.service.RefreshTokenService;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.Result;

@Component
public class RevokeRefreshTokenCommandHandler {

    private final RefreshTokenService _refreshTokenService;

    public RevokeRefreshTokenCommandHandler(RefreshTokenService refreshTokenService) {
        this._refreshTokenService = refreshTokenService;
    }

    public Result<Void> handle(RevokeRefreshTokenCommand command) {
        try {
            // Token iptal işlemini gerçekleştiriyoruz
            _refreshTokenService.revokeToken(command.token());

            // Result.success() metodu parametresiz olarak başarılı durumu ifade eder
            // (Result<Void>)
            return Result.success();
        } catch (Exception e) {
            return Result.failure(e.getMessage());
        }
    }
}