package com.kamilwnek.lift_management.service;

import com.kamilwnek.lift_management.dto.CreateUserRequest;
import com.kamilwnek.lift_management.dto.CreateUserResponse;
import com.kamilwnek.lift_management.entity.User;
import com.kamilwnek.lift_management.exception.NoSuchRecordException;
import com.kamilwnek.lift_management.mapper.CreateUserRequestMapper;
import com.kamilwnek.lift_management.mapper.CreateUserResponseMapper;
import com.kamilwnek.lift_management.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CreateUserRequestMapper createUserRequestMapper;
    private final CreateUserResponseMapper createUserResponseMapper;

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
}
