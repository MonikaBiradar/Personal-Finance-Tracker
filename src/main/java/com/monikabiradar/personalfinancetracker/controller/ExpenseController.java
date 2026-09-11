package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.dto.ExpenseRenameRequest;
import com.monikabiradar.personalfinancetracker.dto.ExpenseRequest;
import com.monikabiradar.personalfinancetracker.entity.Expense;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.exception.UserNotFoundException;
import com.monikabiradar.personalfinancetracker.repository.UserRepository;
import com.monikabiradar.personalfinancetracker.service.ExpenseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ExpenseController {

    private final ExpenseService expenseService;
    private final UserRepository userRepository;

    public ExpenseController(ExpenseService expenseService, UserRepository userRepository) {
        this.expenseService = expenseService;
        this.userRepository = userRepository;
    }

    @PostMapping("/users/{userId}/expenses")
    public Expense addExpense(@PathVariable Long userId, @RequestBody ExpenseRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        return expenseService.addExpense(request.getExpenseName(), user);
    }


    @GetMapping("/users/{userId}/expenses")
    public List<Expense> searchExpenses(@PathVariable Long userId, @RequestParam String name) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        return expenseService.searchExpense(name, user);

    }


    @PatchMapping("/users/{userId}/expenses/{expenseId}/rename")
    public void renameExpense(
            @PathVariable Long userId,
            @PathVariable Long expenseId,
            @RequestBody ExpenseRenameRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        expenseService.renameExpense(expenseId,request.getNewName(),user);
    }


    @PatchMapping("/users/{userId}/expenses/{expenseId}/remove")
    public void removeExpense(@PathVariable Long userId, @PathVariable Long expenseId) {

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        expenseService.removeExpense(expenseId, user);
    }
}
