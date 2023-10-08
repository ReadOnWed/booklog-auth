package com.booklog.global.exception.exceptions;

import com.booklog.global.exception.BookLogApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthenticationException extends BookLogApiException {

    public AuthenticationException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }
}