package com.softdesign.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.server.ResponseStatusException;

public class EntityNotFoundException extends ResponseStatusException {
    public EntityNotFoundException(String reason) {
        super(NOT_FOUND, reason);
    }
}
