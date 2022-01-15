package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.CreateUserRequest;
import com.kamilwnek.lift_management.dto.CreateUserResponse;
import com.kamilwnek.lift_management.dto.LoginRequest;
import com.kamilwnek.lift_management.dto.LoginResponse;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.exception.NoSuchRecordException;
import com.kamilwnek.lift_management.mapper.CreateUserRequestMapper;
import com.kamilwnek.lift_management.mapper.CreateUserResponseMapper;
import com.kamilwnek.lift_management.repository.UserRepository;
import com.kamilwnek.lift_management.security.ApplicationSecurityConfig;
import com.kamilwnek.lift_management.security.JwtAccessTokenUtil;
import lombok.AllArgsConstructor;
import org.apache.catalina.security.SecurityConfig;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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
    private final CreateUserRequestMapper createUserRequestMapper;
    private final CreateUserResponseMapper createUserResponseMapper;
    private final JwtAccessTokenUtil jwtAccessTokenUtil;

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
        User user = userRepository.save(createUserRequestMapper.toEntity(request));

        return createUserResponseMapper.toDto(user);
    }

    public LoginResponse loginUser(LoginRequest request) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User userDetails = (User) authenticate.getPrincipal();

        return new LoginResponse(jwtAccessTokenUtil.createAccessToken(userDetails),"Refresh Token");
    }
}
