package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.refresh.generate;

import com.bootcamp.jwt_and_refresh_token_authentication.application.service.RefreshTokenService;
import com.bootcamp.jwt_and_refresh_token_authentication.application.service.UserService;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.Result;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.User;
import org.springframework.stereotype.Service;

@Service
public class GenerateRefreshTokenCommandHandler {

    private final UserService _userService;
    private final RefreshTokenService _refreshTokenService;

    public GenerateRefreshTokenCommandHandler(UserService userService, RefreshTokenService refreshTokenService) {
        this._userService = userService;
        this._refreshTokenService = refreshTokenService;

    }

    public Result<String> handle(GenerateRefreshTokenCommand command) {
        try {
            // Kullanıcıyı buluyoruz (yoksa hata fırlatacak)
            User user = _userService.getById(command.userId());

            // Eğer oldToken null ise yeni login sayılır, dolu ise refresh işlemi yapılır.
            String newToken = _refreshTokenService.generateRefreshToken(user, command.oldToken());

            return Result.success(newToken);
        } catch (Exception e) {
            // Hata durumunda Result pattern kullanarak hata mesajını dönüyoruz.
            return Result.failure(e.getMessage());
        }
    }
}