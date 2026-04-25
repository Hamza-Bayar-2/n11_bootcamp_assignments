package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.refresh.generate;

import com.bootcamp.jwt_and_refresh_token_authentication.api.dto.LoginResultDto;
import com.bootcamp.jwt_and_refresh_token_authentication.application.service.AccessTokenService;
import com.bootcamp.jwt_and_refresh_token_authentication.application.service.RefreshTokenService;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.Result;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.User;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.UserRefreshToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GenerateRefreshTokenCommandHandler {

    private final RefreshTokenService _refreshTokenService;
    private final AccessTokenService _accessTokenService;

    public GenerateRefreshTokenCommandHandler(RefreshTokenService refreshTokenService,
            AccessTokenService accessTokenService) {
        this._refreshTokenService = refreshTokenService;
        this._accessTokenService = accessTokenService;
    }

    public Result<LoginResultDto> handle(GenerateRefreshTokenCommand command) {

        // Token doğrulama ve kullanıcıyı çekme
        UserRefreshToken oldTokenEntity = _refreshTokenService.validateToken(command.oldToken());
        if (oldTokenEntity == null) {
            return Result.failure("Invalid or expired refresh token");
        }

        User user = oldTokenEntity.getUser();

        // Yeni Refresh Token üretme (eskisini iptal eder ve yenisini kaydeder)
        String newRefreshToken = _refreshTokenService.generateRefreshToken(user, command.oldToken());

        // Kullanıcı rollerini JWT için çekme
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getRoleType().toString())
                .toList();

        // Yeni Access Token (JWT) üretme
        String newAccessToken = _accessTokenService.generateToken(user.getEmail(), roles);

        return Result.success(new LoginResultDto(newAccessToken, newRefreshToken));
    }
}