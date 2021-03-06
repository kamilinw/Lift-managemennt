package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.*;
import com.kamilwnek.lift_management.entity.RefreshToken;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.exception.NoSuchRecordException;
import com.kamilwnek.lift_management.mapper.UserMapper;
import com.kamilwnek.lift_management.repository.UserRepository;
import com.kamilwnek.lift_management.security.JwtTokenUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import javax.validation.ValidationException;
import java.time.Clock;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;
    private final JwtTokenUtil jwtTokenUtil;
    private final Clock clock;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(
                        ()->new NoSuchRecordException(String.format("User with username %s doesn't exist!", username))
                );
    }

    public UserDto create(CreateUserRequest request) {
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new ValidationException("Passwords don't match!");
        }
        User user = userRepository.save(userMapper.toEntity(request));
        return userMapper.toDto(user);
    }

    public LoginResponse loginUser(LoginRequest request, String device) {
        Authentication authentication;
        authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        User userDetails = (User) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createToken(userDetails, device);
        String accessToken = jwtTokenUtil.createAccessToken(userDetails, clock);
        return userMapper.toLoginResponse(userDetails, refreshToken, accessToken);
    }

    public UserDto getUserById(String idString) {
        UUID id = UUID.fromString(idString);
        User user = userRepository.findById(id).orElseThrow(
                () -> new NoSuchRecordException(String.format("User with id %s not found!", idString))
        );
        return userMapper.toDto(user);
    }
}
