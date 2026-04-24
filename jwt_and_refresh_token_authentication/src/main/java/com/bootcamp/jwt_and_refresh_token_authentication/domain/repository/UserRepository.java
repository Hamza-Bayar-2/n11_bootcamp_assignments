package com.bootcamp.jwt_and_refresh_token_authentication.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
