package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.*;
import com.kamilwnek.lift_management.entity.RefreshToken;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.exception.NoSuchRecordException;
import com.kamilwnek.lift_management.mapper.UserMapper;
import com.kamilwnek.lift_management.repository.UserRepository;
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

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;
    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(
                        ()->new NoSuchRecordException(String.format("User with email %s doesn't exist!", username))
                );
    }

    public CreateUserResponse create(CreateUserRequest request) {
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new ValidationException("Passwords don't match!");
        }
        User user = userRepository.save(userMapper.toEntity(request));
        return userMapper.toCreateUserResponse(user);
    }

    public LoginResponse loginUser(LoginRequest request, String device) {
        Authentication authentication;
        authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        User userDetails = (User) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createToken(userDetails, device);

        return userMapper.toLoginResponse(userDetails, refreshToken);
    }

    public UserDto getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new NoSuchRecordException(String.format("User with id %s not found!", id))
        );
        return userMapper.toDto(user);
    }
}
