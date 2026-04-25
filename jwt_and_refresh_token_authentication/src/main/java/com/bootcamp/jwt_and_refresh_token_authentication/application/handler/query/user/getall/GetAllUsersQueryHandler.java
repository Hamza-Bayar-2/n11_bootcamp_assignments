package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.query.user.getall;

import com.bootcamp.jwt_and_refresh_token_authentication.api.dto.UserDto;
import com.bootcamp.jwt_and_refresh_token_authentication.application.service.UserService;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.Result;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GetAllUsersQueryHandler {

    private final UserService _userService;

    public GetAllUsersQueryHandler(UserService userService) {
        this._userService = userService;
    }

    public Result<List<UserDto>> handle(GetAllUsersQuery query) {
        List<UserDto> users = _userService.getAll().stream()
                .map(user -> new UserDto(
                        user.getId(),
                        user.getName(),
                        user.getSurname(),
                        user.getEmail(),
                        user.getRoles().stream().map(r -> r.getRoleType().toString()).collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        return Result.success(users);
    }
}
