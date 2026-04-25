package com.bootcamp.jwt_and_refresh_token_authentication.application.handler.query;

public interface QueryHandler<C, R> {
    R handle(C command);
}
