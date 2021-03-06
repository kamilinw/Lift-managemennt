package com.kamilwnek.lift_management.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.util.Collections;
import java.util.List;

@Getter
@AllArgsConstructor
public class ApiError {
    private final HttpStatus status;
    private final String message;
    private final List<String> errors;
    private final String path;

    public ApiError(HttpStatus status, String message, String error, String path) {
        super();
        this.status = status;
        this.message = message;
        this.path = path;
        errors = Collections.singletonList(error);
    }
}
