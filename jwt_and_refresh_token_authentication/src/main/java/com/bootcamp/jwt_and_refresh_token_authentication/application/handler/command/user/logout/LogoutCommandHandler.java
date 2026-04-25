package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.logout;

import com.bootcamp.jwt_and_refresh_token_authentication.application.service.RefreshTokenService;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.Result;
import org.springframework.stereotype.Component;

@Component
public class LogoutCommandHandler {

    private final RefreshTokenService _refreshTokenService;

    public LogoutCommandHandler(RefreshTokenService refreshTokenService) {
        this._refreshTokenService = refreshTokenService;
    }

    public Result<Void> handle(LogoutCommand command) {

        _refreshTokenService.revokeToken(command.refreshToken());

        return Result.success();
    }
}
