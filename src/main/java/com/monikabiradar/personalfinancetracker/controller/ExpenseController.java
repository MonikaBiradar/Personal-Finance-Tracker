package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.dto.ExpenseRenameRequest;
import com.monikabiradar.personalfinancetracker.dto.ExpenseRequest;
import com.monikabiradar.personalfinancetracker.entity.Expense;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.service.ExpenseService;
import com.monikabiradar.personalfinancetracker.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserService userService;

    public ExpenseController(ExpenseService expenseService, UserService userService) {
        this.expenseService = expenseService;
        this.userService = userService;
    }

    @PostMapping("/users/{userId}/expenses")
    public Expense addExpense(@PathVariable Long userId, @RequestBody ExpenseRequest request) {

        User user = userService.findActiveUser(userId);

        return expenseService.addExpense(request.getExpenseName(), user);
    }


    @GetMapping("/users/{userId}/expenses")
    public List<Expense> searchExpenses(@PathVariable Long userId, @RequestParam String name) {

        User user = userService.findActiveUser(userId);

        return expenseService.searchExpense(name, user);

    }


    @PatchMapping("/users/{userId}/expenses/{expenseId}/rename")
    public void renameExpense(
            @PathVariable Long userId,
            @PathVariable Long expenseId,
            @RequestBody ExpenseRenameRequest request) {

        User user = userService.findActiveUser(userId);

        expenseService.renameExpense(expenseId,request.getNewName(),user);
    }


    @PatchMapping("/users/{userId}/expenses/{expenseId}/remove")
    public void removeExpense(@PathVariable Long userId, @PathVariable Long expenseId) {

        User user = userService.findActiveUser(userId);

        expenseService.removeExpense(expenseId, user);
    }
}
