package com.bootcamp.jwt_and_refresh_token_authentication.api.controller;

import com.bootcamp.jwt_and_refresh_token_authentication.api.dto.UserDto;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.deleteall.DeleteAllUsersCommand;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.deleteall.DeleteAllUsersCommandHandler;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.deletebyemail.DeleteUserByEmailCommand;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command.user.deletebyemail.DeleteUserByEmailCommandHandler;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.query.user.getall.GetAllUsersQuery;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.query.user.getall.GetAllUsersQueryHandler;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.query.user.getme.GetCurrentUserQuery;
import com.bootcamp.jwt_and_refresh_token_authentication.application.handler.query.user.getme.GetCurrentUserQueryHandler;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final GetAllUsersQueryHandler _getAllUsersQueryHandler;
    private final DeleteAllUsersCommandHandler _deleteAllUsersCommandHandler;
    private final GetCurrentUserQueryHandler _getCurrentUserQueryHandler;
    private final DeleteUserByEmailCommandHandler _deleteUserByEmailCommandHandler;

    public UserController(GetAllUsersQueryHandler getAllUsersQueryHandler,
                          DeleteAllUsersCommandHandler deleteAllUsersCommandHandler,
                          GetCurrentUserQueryHandler getCurrentUserQueryHandler,
                          DeleteUserByEmailCommandHandler deleteUserByEmailCommandHandler) {
        this._getAllUsersQueryHandler = getAllUsersQueryHandler;
        this._deleteAllUsersCommandHandler = deleteAllUsersCommandHandler;
        this._getCurrentUserQueryHandler = getCurrentUserQueryHandler;
        this._deleteUserByEmailCommandHandler = deleteUserByEmailCommandHandler;
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        Result<List<UserDto>> result = _getAllUsersQueryHandler.handle(new GetAllUsersQuery());
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result.errorMessage());
        }
        return ResponseEntity.ok(result.data());
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAllUsers() {
        Result<Void> result = _deleteAllUsersCommandHandler.handle(new DeleteAllUsersCommand());
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result.errorMessage());
        }
        return ResponseEntity.ok("All users deleted successfully");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMe() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return ResponseEntity.status(401).body("No active session");
        }

        Result<UserDto> result = _getCurrentUserQueryHandler.handle(new GetCurrentUserQuery(auth.getName()));
        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result.errorMessage());
        }
        return ResponseEntity.ok(result.data());
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> deleteByEmail(@PathVariable String email) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || auth.getName().equals("anonymousUser")) {
            return ResponseEntity.status(401).body("No active session");
        }

        boolean isAdmin = auth.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        DeleteUserByEmailCommand command = new DeleteUserByEmailCommand(email, auth.getName(), isAdmin);
        Result<Void> result = _deleteUserByEmailCommandHandler.handle(command);

        if (!result.isSuccess()) {
            return ResponseEntity.badRequest().body(result.errorMessage());
        }
        return ResponseEntity.ok("User deleted successfully");
    }
}
