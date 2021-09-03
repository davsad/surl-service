package com.surl.service.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.NoArgsConstructor;

@ResponseStatus(HttpStatus.NOT_FOUND)
@NoArgsConstructor
public class InvalidUrlException extends Exception {

    private static final long serialVersionUID = 2968310277674447646L;
    private static final String ERROR_MSG = "Invalid Url Exception {%s}";

    public InvalidUrlException(String message) {
        super(String.format(ERROR_MSG, message));
    }
}
