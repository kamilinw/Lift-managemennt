package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.RefreshTokenRequest;
import com.kamilwnek.lift_management.dto.RefreshTokenResponse;
import com.kamilwnek.lift_management.entity.RefreshToken;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.exception.JwtTokenException;
import com.kamilwnek.lift_management.repository.RefreshTokenRepository;
import com.kamilwnek.lift_management.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final Clock clock;

    public RefreshToken createToken(User user, String device) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(user)
                .expiryDate(jwtTokenUtil.createExpiryDate(clock))
                .token(UUID.randomUUID().toString())
                .deviceName(device)
                .build();
        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional
    public RefreshTokenResponse refreshToken(RefreshTokenRequest request, String device) {
        return refreshTokenRepository.findByTokenAndDeviceName(request.getRefreshToken(), device)
                .map(this::verifyExpiration)
                .map(refreshToken -> {
                    User user = refreshToken.getUser();
                    refreshToken.setToken(UUID.randomUUID().toString());
                    refreshToken.setExpiryDate(jwtTokenUtil.createExpiryDate(clock));
                    return new RefreshTokenResponse(
                            jwtTokenUtil.createAccessToken(user, clock),
                            refreshToken.getToken(),
                            "Bearer");
                })
                .orElseThrow(()-> new JwtTokenException("Wrong Refresh Token!"));
    }

    private RefreshToken verifyExpiration(RefreshToken refreshToken){
        if (refreshToken.getExpiryDate().compareTo(LocalDateTime.now(clock)) < 0){
            refreshTokenRepository.delete(refreshToken);
            throw new JwtTokenException("Refresh token was expired. Please make a new sign in request");
        }
        return refreshToken;
    }
}
