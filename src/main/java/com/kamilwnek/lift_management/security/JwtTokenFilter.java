package com.kamilwnek.lift_management.security;

import com.kamilwnek.lift_management.exception.JwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

        // Get authorization header and validate
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (isEmpty(header) || !header.startsWith(jwtConfig.getTokenPrefix())) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.split(" ")[1].trim();
        try {
            jwtAccessTokenUtil.validate(token);
        } catch (JwtTokenException e){
            filterChain.doFilter(request, response);
            return;
        }


        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(jwtSecretKey.secretKey()).build().parseClaimsJws(token);
        List<Map<String,String>> authoritiesList = (List<Map<String,String>>) claims.getBody().get(AUTHORITIES);

        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authoritiesList.stream()
                .map(m -> new SimpleGrantedAuthority(m.get(AUTHORITY)))
                .collect(Collectors.toSet());

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                claims.getBody().getSubject(),
                null,
                simpleGrantedAuthorities
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}
