package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.deleteall;

import com.bootcamp.jwt_and_refresh_token_authentication.application.service.UserService;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.Result;
import org.springframework.stereotype.Component;

@Component
public class DeleteAllUsersCommandHandler {

    private final UserService _userService;

    public DeleteAllUsersCommandHandler(UserService userService) {
        this._userService = userService;
    }

    public Result<Void> handle(DeleteAllUsersCommand command) {
        _userService.deleteAll();
        return Result.success();
    }
}
