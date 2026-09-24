package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.entity.Transaction;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.enums.TransactionStatus;
import com.monikabiradar.personalfinancetracker.enums.TransactionType;
import com.monikabiradar.personalfinancetracker.exception.UserNotFoundException;
import com.monikabiradar.personalfinancetracker.repository.UserRepository;
import com.monikabiradar.personalfinancetracker.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;
    private final UserRepository userRepository;

    public TransactionController(TransactionService transactionService, UserRepository userRepository) {
        this.transactionService = transactionService;
        this.userRepository = userRepository;
    }

    @PostMapping("/users/{userId}/transactions")
    public ResponseEntity<Transaction> addTransaction(@PathVariable Long userId, @RequestParam TransactionType transactionType, @RequestParam BigDecimal amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        Transaction transaction = transactionService.addTransaction(transactionType, amount, user);
        return ResponseEntity.status(HttpStatus.CREATED).body(transaction);
    }

    @GetMapping("/users/{userId}/transactions")
    public ResponseEntity<List<Transaction>> displayTransaction(@PathVariable Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        return ResponseEntity.ok(transactionService.displayTransaction(user));
    }

    @GetMapping("/users/{userId}/transactions/search")
    public ResponseEntity<List<Transaction>> searchTransaction(
            @PathVariable Long userId,
            @RequestParam(required = false) LocalDate date,
            @RequestParam(required = false) TransactionType transactionType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        return ResponseEntity.ok(transactionService.searchTransaction(date, transactionType, user));
    }

    @GetMapping("/users/{userId}/transactions/filter")
    public ResponseEntity<List<Transaction>> filterTransaction(
            @PathVariable Long userId,
            @RequestParam(required = false) BigDecimal minAmount,
            @RequestParam(required = false) BigDecimal maxAmount,
            @RequestParam(required = false) TransactionStatus status,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        return ResponseEntity.ok(transactionService.filterTransaction(
                user, minAmount, maxAmount, status, startDate, endDate));
    }

    @GetMapping("/users/{userId}/transactions/sort")
    public ResponseEntity<List<Transaction>> sortTransaction(@PathVariable Long userId, @RequestParam String sortBy, @RequestParam String order) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        return ResponseEntity.ok(transactionService.sortTransaction(user, sortBy, order));
    }

    @PatchMapping("/users/{userId}/transactions/{transactionId}/retract")
    public ResponseEntity<Transaction> retractTransaction(@PathVariable Long userId, @PathVariable Long transactionId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found."));

        return ResponseEntity.ok(transactionService.retractTransaction(transactionId, user));
    }
}
