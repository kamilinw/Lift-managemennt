package com.kamilwnek.lift_management.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class JwtTokenException extends RuntimeException{
    public JwtTokenException(String message){
        super(message);
    }
}
