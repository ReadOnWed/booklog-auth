package com.booklog.global.exception.exceptions;

import com.booklog.global.exception.BookLogApiException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class ForbiddenException extends BookLogApiException {

    public ForbiddenException(String reason) {
        super(HttpStatus.FORBIDDEN, reason);
    }
}
