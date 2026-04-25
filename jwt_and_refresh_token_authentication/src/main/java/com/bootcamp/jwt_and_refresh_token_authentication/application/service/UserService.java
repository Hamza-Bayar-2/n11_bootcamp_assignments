package com.bootcamp.jwt_and_refresh_token_authentication.application.service;

import java.util.List;

import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.User;

public interface UserService {
    List<User> getAll();

    User getById(Long id);

    User getByEmail(String email);

    User save(User user);

    User update(User user);

    Boolean delete(Long id);

}
