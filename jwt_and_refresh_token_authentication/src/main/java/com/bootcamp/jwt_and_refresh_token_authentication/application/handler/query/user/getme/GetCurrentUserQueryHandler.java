package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.query.user.getme;

import com.bootcamp.jwt_and_refresh_token_authentication.api.dto.UserDto;
import com.bootcamp.jwt_and_refresh_token_authentication.application.service.UserService;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.Result;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class GetCurrentUserQueryHandler {

    private final UserService _userService;

    public GetCurrentUserQueryHandler(UserService userService) {
        this._userService = userService;
    }

    public Result<UserDto> handle(GetCurrentUserQuery query) {
        try {
            User user = _userService.getByEmail(query.email());
            
            UserDto userDto = new UserDto(
                    user.getId(),
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    user.getRoles().stream().map(r -> r.getRoleType().toString()).collect(Collectors.toList())
            );

            return Result.success(userDto);
        } catch (Exception e) {
            return Result.failure("User not found or session invalid");
        }
    }
}
