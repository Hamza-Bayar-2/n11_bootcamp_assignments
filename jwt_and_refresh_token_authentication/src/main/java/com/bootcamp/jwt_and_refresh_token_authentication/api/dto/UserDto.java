package com.bootcamp.jwt_and_refresh_token_authentication.api.dto;

import java.util.List;

public record UserDto(
    Long id,
    String name,
    String surname,
    String email,
    List<String> roles
) {
}
