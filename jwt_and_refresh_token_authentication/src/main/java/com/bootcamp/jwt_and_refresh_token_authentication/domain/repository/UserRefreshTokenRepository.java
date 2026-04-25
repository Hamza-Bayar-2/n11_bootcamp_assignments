package com.bootcamp.jwt_and_refresh_token_authentication.domain.repository;

import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.User;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.UserRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {
    UserRefreshToken findByToken(String token);
    void deleteByUser(User user);
}