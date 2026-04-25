package com.bootcamp.jwt_and_refresh_token_authentication.domain;

public record Result<T>(T data, boolean isSuccess, String errorMessage) {

    public static <T> Result<T> success(T data) {
        return new Result<>(data, true, null);
    }

    public static <T> Result<T> failure(String errorMessage) {
        return new Result<>(null, false, errorMessage);
    }

    // For results that wont retun value
    public static Result<Void> success() {
        return new Result<>(null, true, null);
    }
}