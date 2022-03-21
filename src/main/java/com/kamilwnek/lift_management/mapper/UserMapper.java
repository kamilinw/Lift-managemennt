package com.kamilwnek.lift_management.mapper;

import com.kamilwnek.lift_management.dto.CreateUserRequest;
import com.kamilwnek.lift_management.dto.CreateUserResponse;
import com.kamilwnek.lift_management.dto.LoginResponse;
import com.kamilwnek.lift_management.dto.UserDto;
import com.kamilwnek.lift_management.entity.RefreshToken;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements DtoMapper<User, UserDto> {
    private final PasswordEncoder passwordEncoder;
    @Override
    public User toEntity(UserDto dto) {
        return null;
    }

    @Override
    public UserDto toDto(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getApplicationUserRole()
        );
    }

    public LoginResponse toLoginResponse(User userDetails, RefreshToken refreshToken, String accessToken){
        return new LoginResponse(
                toDto(userDetails),
                accessToken,
                refreshToken.getToken(),
                "Bearer"
        );
    }

    public User toEntity(CreateUserRequest request){
        return new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                request.getEmail()
        );
    }
}
