package com.monikabiradar.personalfinancetracker.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AllocationConflictException.class)
    public ResponseEntity<String> handleAllocationConflict(AllocationConflictException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(BudgetAllocationNotFoundException.class)
    public ResponseEntity<String> handleBudgetAllocationNotFound(BudgetAllocationNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(BudgetExistsException.class)
    public ResponseEntity<String> handleBudgetExists(BudgetExistsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(BudgetNotFoundException.class)
    public ResponseEntity<String> handleBudgetNotFound(BudgetNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(DuplicateAllocationException.class)
    public ResponseEntity<String> handleDuplicateAllocation(DuplicateAllocationException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(DuplicateExpenseException.class)
    public ResponseEntity<String> handleDuplicateExpense(DuplicateExpenseException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(DuplicateIncomeException.class)
    public ResponseEntity<String> handleDuplicateIncome(DuplicateIncomeException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(DuplicateSavingsException.class)
    public ResponseEntity<String> handleDuplicateSavings(DuplicateSavingsException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<String> handleExpenseNotFound(ExpenseNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(IncomeNotFoundException.class)
    public ResponseEntity<String> handleIncomeNotFound(IncomeNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(InsufficientBudgetException.class)
    public ResponseEntity<String> handleInsufficientBudget(InsufficientBudgetException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }

    @ExceptionHandler(SavingsNotFoundException.class)
    public ResponseEntity<String> handleSavingsNotFound(SavingsNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(TransactionNotFoundException.class)
    public ResponseEntity<String> handleTransactionNotFound(TransactionNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UserConflictException.class)
    public ResponseEntity<String> handleUserConflict(UserConflictException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFound(UserNotFoundException exception) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException exception) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exception.getMessage());
    }
}