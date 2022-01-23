package com.kamilwnek.lift_management.mapper;

import com.kamilwnek.lift_management.dto.LoginResponse;
import com.kamilwnek.lift_management.dto.UserDto;
import com.kamilwnek.lift_management.entity.RefreshToken;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.security.JwtAccessTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements DtoMapper<User, UserDto> {
    private final JwtAccessTokenUtil jwtAccessTokenUtil;
    @Override
    public User toEntity(UserDto dto) {
        return null;
    }

    @Override
    public UserDto toDto(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getCreatedAt(),
                entity.getModifiedAt(),
                entity.getUsername(),
                entity.getEmail(),
                entity.getApplicationUserRole()
        );
    }

    public LoginResponse toLoginResponse(User userDetails, RefreshToken refreshToken){
        return new LoginResponse(
                userDetails.getId(),
                userDetails.getCreatedAt(),
                userDetails.getModifiedAt(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getApplicationUserRole(),
                jwtAccessTokenUtil.createAccessToken(userDetails),
                refreshToken.getToken(),
                "Bearer"
        );
    }
}
