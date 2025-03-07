package com.project.stream.service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatabaseException extends RuntimeException{

    public DatabaseException(String msg) {
        super(msg);
    }

    public DatabaseException(String msg, Object...args) {
        super(String.format(msg, args));
    }
}

