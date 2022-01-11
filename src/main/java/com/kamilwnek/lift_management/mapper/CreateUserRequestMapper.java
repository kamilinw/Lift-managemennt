package com.kamilwnek.lift_management.mapper;

import com.kamilwnek.lift_management.dto.CreateUserRequest;
import com.kamilwnek.lift_management.entity.User;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CreateUserRequestMapper implements DtoMapper<User, CreateUserRequest> {
    private final PasswordEncoder passwordEncoder;
    @Override
    public User toEntity(CreateUserRequest dto) {
        return new User(
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                dto.getEmail()
        );
    }

    @Override
    public CreateUserRequest toDto(User entity) {
        return new CreateUserRequest(
                entity.getUsername(),
                entity.getEmail(),
                entity.getPassword(),
                entity.getPassword()
        );
    }
}
