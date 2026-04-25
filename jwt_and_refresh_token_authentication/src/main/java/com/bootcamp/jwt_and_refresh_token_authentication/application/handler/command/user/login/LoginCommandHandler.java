package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.login;

import com.bootcamp.jwt_and_refresh_token_authentication.api.dto.LoginResultDto;
import com.bootcamp.jwt_and_refresh_token_authentication.application.service.AccessTokenService;
import com.bootcamp.jwt_and_refresh_token_authentication.application.service.RefreshTokenService;
import com.bootcamp.jwt_and_refresh_token_authentication.application.service.UserService;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.Result;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.User;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.enums.RoleType;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class LoginCommandHandler {

    private final UserService _userService;
    private final PasswordEncoder _passwordEncoder;
    private final AccessTokenService _accessTokenService;
    private final RefreshTokenService _refreshTokenService;

    public LoginCommandHandler(UserService userService,
            PasswordEncoder passwordEncoder,
            AccessTokenService accessTokenService,
            RefreshTokenService refreshTokenService) {
        this._userService = userService;
        this._passwordEncoder = passwordEncoder;
        this._accessTokenService = accessTokenService;
        this._refreshTokenService = refreshTokenService;
    }

    public Result<LoginResultDto> handle(LoginCommand command) {
        try {
            // Kullanıcıyı emaile göre DB'den getiriyoruz
            User user = _userService.getByEmail(command.email());

            if(user == null) 
                return Result.failure("User not found with the given E-mail");

            // Parolayı (Hashlenmiş) kontrol ediyoruz
            if (!_passwordEncoder.matches(command.password(), user.getPassword())) {
                return Result.failure("Invalid email or password");
            }

            // Kullanıcının gerçek rollerini çekiyoruz
            List<String> roles = user.getRoles().stream()
                    .map(role -> role.getRoleType().toString())
                    .toList();

            if (roles.isEmpty()) {
                // Eğer rolü yoksa varsayılan USER rolü verilebilir
                roles = List.of(RoleType.USER.toString());
            }

            // Yeni JWT ve Refresh Token'ları üretiyoruz
            String jwtToken = _accessTokenService.generateToken(user.getEmail(), roles);
            String refreshToken = _refreshTokenService.generateRefreshToken(user, null);

            // DTO olarak her iki tokenı kapsülleyip controllera yolluyoruz
            return Result.success(new LoginResultDto(jwtToken, refreshToken));

        } catch (Exception e) {
            return Result.failure("User not found or an error occurred");
        }
    }
}