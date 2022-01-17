package com.kamilwnek.lift_management.mapper;

import com.kamilwnek.lift_management.dto.UserDto;
import com.kamilwnek.lift_management.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements DtoMapper<User, UserDto> {
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
}
