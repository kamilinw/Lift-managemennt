package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.entity.RefreshToken;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.repository.RefreshTokenRepository;
import com.kamilwnek.lift_management.security.JwtConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtConfig jwtConfig;


    public RefreshToken createToken(User user, String device) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expiryDate(Instant.now().plusMillis(jwtConfig.getRefreshTokenExpiration()))
                .token(UUID.randomUUID().toString())
                .deviceName(device)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }
}
