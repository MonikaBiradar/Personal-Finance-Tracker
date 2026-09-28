package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.dto.BudgetRequest;
import com.monikabiradar.personalfinancetracker.entity.Budget;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.exception.UserNotFoundException;
import com.monikabiradar.personalfinancetracker.repository.UserRepository;
import com.monikabiradar.personalfinancetracker.service.BudgetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BudgetController {

    private final BudgetService budgetService;
    private final UserRepository userRepository;

    public BudgetController(BudgetService budgetService, UserRepository userRepository) {
        this.budgetService = budgetService;
        this.userRepository = userRepository;
    }

    @PostMapping("/users/{userId}/budgets")
    public ResponseEntity<Budget> addBudget(@PathVariable Long userId, @RequestBody BudgetRequest request) {
       User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found"));

       Budget budget = budgetService.addBudget(request, user);

       return ResponseEntity.status(HttpStatus.CREATED).body(budget);
    }

    @PatchMapping("/users/{userId}/budgets/{budgetId}/remove")
    public ResponseEntity<Budget> removeBudget(@PathVariable Long userId, @PathVariable Long budgetId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found"));

        Budget budget = budgetService.removeBudget(budgetId, user);

        return ResponseEntity.status(HttpStatus.OK).body(budget);
    }

}
