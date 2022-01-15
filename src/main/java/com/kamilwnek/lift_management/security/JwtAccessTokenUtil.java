package com.kamilwnek.lift_management.security;

import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.exception.JwtTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtAccessTokenUtil {
    private final JwtConfig jwtConfig;
    private final JwtSecretKey jwtSecretKey;

    public String createAccessToken(User user){
        return Jwts.builder()
                .setSubject(user.getId().toString())
                .claim("authorities", user.getAuthorities())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+ Duration.ofMinutes(jwtConfig.getAcceptTokenExpiration()).toMillis()))
                .signWith(jwtSecretKey.secretKey())
                .compact();
    }

    public Long getUserId(String jwtToken){
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

    public boolean validate(String token) {
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
}
