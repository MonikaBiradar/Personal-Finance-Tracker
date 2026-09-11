package com.monikabiradar.personalfinancetracker.exception;

public class UserConflictException extends RuntimeException {

    public UserConflictException(String message) {
        super(message);
    }
}
