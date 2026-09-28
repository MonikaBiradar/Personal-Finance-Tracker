package com.monikabiradar.personalfinancetracker.exception;

public class BudgetExistsException extends RuntimeException {

    public BudgetExistsException(String message) {
        super(message);
    }
}
