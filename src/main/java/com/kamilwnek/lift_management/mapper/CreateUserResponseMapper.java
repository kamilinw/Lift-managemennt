package com.kamilwnek.lift_management.mapper;

import com.kamilwnek.lift_management.dto.CreateUserResponse;
import com.kamilwnek.lift_management.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CreateUserResponseMapper implements DtoMapper<User, CreateUserResponse> {
    @Override
    public User toEntity(CreateUserResponse dto) {
        return null;
    }

    @Override
    public CreateUserResponse toDto(User entity) {
        return new CreateUserResponse(
                entity.getId(),
                entity.getUsername(),
                entity.getEmail()
        );
    }
}
