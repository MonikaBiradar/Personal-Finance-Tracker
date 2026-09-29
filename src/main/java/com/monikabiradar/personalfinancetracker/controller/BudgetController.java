package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.dto.BudgetRequest;
import com.monikabiradar.personalfinancetracker.entity.Budget;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.service.BudgetService;
import com.monikabiradar.personalfinancetracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BudgetController {

    private final BudgetService budgetService;
    private final UserService userService;

    public BudgetController(BudgetService budgetService, UserService userService) {
        this.budgetService = budgetService;
        this.userService = userService;
    }

    @PostMapping("/users/{userId}/budgets")
    public ResponseEntity<Budget> addBudget(@PathVariable Long userId, @RequestBody BudgetRequest request) {
        User user = userService.findActiveUser(userId);

        Budget budget = budgetService.addBudget(request, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(budget);
    }

    @PatchMapping("/users/{userId}/budgets/{budgetId}/remove")
    public ResponseEntity<Budget> removeBudget(@PathVariable Long userId, @PathVariable Long budgetId) {
        User user = userService.findActiveUser(userId);

        Budget budget = budgetService.removeBudget(budgetId, user);

        return ResponseEntity.status(HttpStatus.OK).body(budget);
    }

}
