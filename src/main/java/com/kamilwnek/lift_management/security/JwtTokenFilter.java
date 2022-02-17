package com.kamilwnek.lift_management.security;

import com.kamilwnek.lift_management.exception.JwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.util.ObjectUtils.isEmpty;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtConfig jwtConfig;
    private final JwtSecretKey jwtSecretKey;
    private final JwtAccessTokenUtil jwtAccessTokenUtil;
    private static final String AUTHORITIES = "authorities";
    private static final String AUTHORITY = "authority";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (!isHeaderCorrect(header)) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = jwtAccessTokenUtil.getTokenFromHeader(header);
        if (!isTokenValid(token)){
            filterChain.doFilter(request, response);
            return;
        }

        authenticateUserWithAuthorities(token);
        filterChain.doFilter(request, response);
    }

    private boolean isHeaderCorrect(String header){
        return isEmpty(header) || !header.startsWith(jwtConfig.getTokenPrefix());
    }

    private boolean isTokenValid(String token){
        try {
            jwtAccessTokenUtil.validate(token);
            return true;
        } catch (JwtTokenException e){
            return false;
        }
    }

    private Claims getBodyFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(jwtSecretKey.secretKey()).build().parseClaimsJws(token).getBody();
    }

    private Set<SimpleGrantedAuthority> mapAuthoritiesToSet(List<Map<String,String>> authorities){
        return authorities.stream()
                .map(m -> new SimpleGrantedAuthority(m.get(AUTHORITY)))
                .collect(Collectors.toSet());
    }

    private void authenticateUserWithAuthorities(String token){
        Claims body = getBodyFromToken(token);
        List<Map<String,String>> authorities = (List<Map<String,String>>) body.get(AUTHORITIES);
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = mapAuthoritiesToSet(authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                body.getSubject(),
                null,
                simpleGrantedAuthorities
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
