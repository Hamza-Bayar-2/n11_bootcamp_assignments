package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.register;

import com.bootcamp.jwt_and_refresh_token_authentication.api.dto.RegisterResultDto;
import com.bootcamp.jwt_and_refresh_token_authentication.application.service.AccessTokenService;
import com.bootcamp.jwt_and_refresh_token_authentication.application.service.RefreshTokenService;
import com.bootcamp.jwt_and_refresh_token_authentication.application.service.UserService;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.Result;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.User;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.UserRole;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.enums.RoleType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class RegisterCommandHandler {

    private final UserService _userService;
    private final PasswordEncoder _passwordEncoder;
    private final AccessTokenService _accessTokenService;
    private final RefreshTokenService _refreshTokenService;

    public RegisterCommandHandler(UserService userService,
            PasswordEncoder passwordEncoder,
            AccessTokenService accessTokenService,
            RefreshTokenService refreshTokenService) {
        this._userService = userService;
        this._passwordEncoder = passwordEncoder;
        this._accessTokenService = accessTokenService;
        this._refreshTokenService = refreshTokenService;
    }

    public Result<RegisterResultDto> handle(RegisterCommand command) {
        User userWithEmail = _userService.getByEmail(command.email());

        if (userWithEmail != null)
            return Result.failure("E-mail already exist");

        User user = new User();
        user.setName(command.name());
        user.setSurname(command.surname());
        user.setEmail(command.email());
        user.setPassword(_passwordEncoder.encode(command.password()));
        user.setCreatedAt(LocalDateTime.now());

        // Varsayılan USER rolünü atıyoruz
        UserRole userRole = new UserRole();
        userRole.setRoleType(RoleType.USER);
        userRole.setUser(user);
        UserRole userRole2 = new UserRole();
        userRole2.setRoleType(RoleType.ADMIN);
        userRole2.setUser(user);
        user.getRoles().add(userRole);
        user.getRoles().add(userRole2);

        _userService.save(user);

        // JWT için rolleri çekiyoruz
        List<String> roles = user.getRoles().stream()
                .map(role -> role.getRoleType().toString())
                .toList();

        // Register sonrası otomatik login için tokenları üretiyoruz
        String jwtToken = _accessTokenService.generateToken(user.getEmail(), roles);
        String refreshToken = _refreshTokenService.generateRefreshToken(user, null);

        return Result.success(new RegisterResultDto(user, jwtToken, refreshToken));
    }

}
