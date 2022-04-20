package com.kamilwnek.lift_management.security;

import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.exception.JwtTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.Clock;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {
    private final JwtConfig jwtConfig;
    private final JwtSecretKey jwtSecretKey;
    private final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    public String createAccessToken(User user, Clock clock){
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(
                        new Date(clock.millis() + getExpirationOffset())
                )
                .signWith(jwtSecretKey.secretKey())
                .compact();
    }

    private long getExpirationOffset() {
        return Duration.ofMinutes(jwtConfig.getAcceptTokenExpirationMinutes()).toMillis();
    }

    public String getTokenFromHttpAuthorizationHeader(String header){
        return header != null ? header.split(" ")[1].trim() : "";
    }

    public Long getUserIdFromToken(String jwtToken){
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey.secretKey())
                .build()
                .parseClaimsJws(jwtToken);
        return Long.parseLong(claims.getBody().getSubject());
    }

    public Date getExpirationDate(String jwtToken){
        Jws<Claims> claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey.secretKey())
                .build()
                .parseClaimsJws(jwtToken);
        return claims.getBody().getExpiration();
    }

    public boolean validate(String token) throws JwtTokenException {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecretKey.secretKey()).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException ex) {
            throw new JwtTokenException("Invalid JWT signature");
        } catch (MalformedJwtException ex) {
            throw new JwtTokenException("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            throw new JwtTokenException("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            throw new JwtTokenException("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            throw new JwtTokenException("JWT claims string is empty");
        }
    }

    public LocalDateTime createExpiryDate(Clock clock) {
        return LocalDateTime.now(clock).plus(jwtConfig.getRefreshTokenExpirationHours(), ChronoUnit.HOURS);
    }
}
