package com.monikabiradar.personalfinancetracker.exception;

public class InsufficientBudgetException extends RuntimeException {

    public InsufficientBudgetException(String message) {
        super(message);
    }
}
