package com.softdesign.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.server.ResponseStatusException;

public class UnableToVoteException extends ResponseStatusException {
    public UnableToVoteException() {
        super(NOT_FOUND, "UNABLE_TO_VOTE");
    }
}
