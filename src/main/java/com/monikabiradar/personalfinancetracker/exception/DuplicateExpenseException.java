package com.monikabiradar.personalfinancetracker.exception;

public class DuplicateExpenseException extends RuntimeException {

    public DuplicateExpenseException(String message) {
        super(message);
    }
}
