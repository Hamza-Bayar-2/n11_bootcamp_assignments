package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.command;

public interface CommandHandler<C, R> {
    R handle(C command);
}
