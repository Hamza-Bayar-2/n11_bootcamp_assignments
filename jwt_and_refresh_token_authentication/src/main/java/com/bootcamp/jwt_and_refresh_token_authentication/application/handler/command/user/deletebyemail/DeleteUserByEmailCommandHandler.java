package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.deletebyemail;

import com.bootcamp.jwt_and_refresh_token_authentication.application.service.UserService;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.Result;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class DeleteUserByEmailCommandHandler {

    private final UserService _userService;

    public DeleteUserByEmailCommandHandler(UserService userService) {
        this._userService = userService;
    }

    public Result<Void> handle(DeleteUserByEmailCommand command) {
        User user = _userService.getByEmail(command.email());
        if (user == null) {
            return Result.failure("User not found with email: " + command.email());
        }

        // Yetki kontrolü: Sadece admin silebilir ya da kullanıcı kendi hesabını silebilir.
        if (!command.isAdmin() && !command.email().equals(command.currentSessionEmail())) {
            return Result.failure("You don't have permission to delete this user");
        }

        try {
            _userService.delete(user.getId());
            return Result.success();
        } catch (Exception e) {
            return Result.failure("An error occurred while deleting the user: " + e.getMessage());
        }
    }
}
