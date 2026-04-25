package com.bootcamp.jwt_and_refresh_token_authentication.application.service.impl;

import com.bootcamp.jwt_and_refresh_token_authentication.application.service.RefreshTokenService;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.User;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.entity.UserRefreshToken;
import com.bootcamp.jwt_and_refresh_token_authentication.domain.repository.UserRefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {

    private final UserRefreshTokenRepository _refreshTokenRepository;

    public RefreshTokenServiceImpl(UserRefreshTokenRepository refreshTokenRepository) {
        this._refreshTokenRepository = refreshTokenRepository;
    }

    @Value("${jwt.refresh.expiration}")
    private long refreshExpirationMs;

    @Override
    @Transactional
    public String generateRefreshToken(User user, String oldToken) {
        UserRefreshToken validatedOldToken = null;

        if (oldToken != null) {
            validatedOldToken = validateToken(oldToken);
            if (validatedOldToken == null) {
                throw new RuntimeException("Invalid or expired refresh token");
            }
        }

        String newTokenString = UUID.randomUUID().toString();
        UserRefreshToken newTokenEntity = createNewTokenEntity(user, newTokenString);
        _refreshTokenRepository.save(newTokenEntity);

        if (validatedOldToken != null) {
            tokenRotation(validatedOldToken, newTokenEntity);
        }

        return newTokenString;
    }

    @Override
    public UserRefreshToken validateToken(String token) {
        var rt = getByToken(token);

        if (rt != null && !rt.isRevoked() && rt.getExpiryDate().isAfter(LocalDateTime.now())) {
            return rt;
        }

        return null;
    }

    @Override
    @Transactional
    public void revokeToken(String token) {
        UserRefreshToken rt = getByToken(token);
        if (rt != null) {
            rt.setRevoked(true);
            _refreshTokenRepository.save(rt);
        }
    }

    private UserRefreshToken getByToken(String token) {
        return _refreshTokenRepository.findByToken(token);
    }

    @Transactional
    private void tokenRotation(UserRefreshToken oldRt, UserRefreshToken newRt) {
        oldRt.setRevoked(true);
        oldRt.setReplacedBy(newRt.getId());
        _refreshTokenRepository.save(oldRt);
    }

    private UserRefreshToken createNewTokenEntity(User user, String tokenString) {
        return UserRefreshToken.builder()
                .user(user)
                .token(tokenString)
                .expiryDate(LocalDateTime.now().plusNanos(refreshExpirationMs * 1_000_000))
                .createdAt(LocalDateTime.now())
                .revoked(false)
                .build();
    }
}
