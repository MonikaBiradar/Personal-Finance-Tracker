package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.dto.FrequencyUpdateRequest;
import com.monikabiradar.personalfinancetracker.dto.IncomeAmountRequest;
import com.monikabiradar.personalfinancetracker.dto.IncomeRenameRequest;
import com.monikabiradar.personalfinancetracker.dto.IncomeRequest;
import com.monikabiradar.personalfinancetracker.entity.Income;
import com.monikabiradar.personalfinancetracker.entity.Transaction;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.service.IncomeService;
import com.monikabiradar.personalfinancetracker.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
public class IncomeController {

    private final IncomeService incomeService;
    private final UserService userService;

    public IncomeController(IncomeService incomeService, UserService userService) {
        this.incomeService = incomeService;
        this.userService = userService;
    }

    @PostMapping("/users/{userId}/incomes")
    public Income addIncome(@PathVariable Long userId, @RequestBody IncomeRequest request){

        User user = userService.findActiveUser(userId);

        return incomeService.addIncome(request, user);
    }

    @GetMapping("/users/{userId}/incomes")
    public List<Income> searchIncome(@PathVariable Long userId, @RequestParam String name){

        User user = userService.findActiveUser(userId);

        return incomeService.searchIncome(name, user);
    }

    @PatchMapping("/users/{userId}/incomes/{incomeId}/frequency")
    public void updateFrequency(@PathVariable Long userId, @PathVariable Long incomeId, @RequestBody FrequencyUpdateRequest request){

        User user = userService.findActiveUser(userId);

        incomeService.updateFrequency(incomeId,request.getFrequency(),user);
    }

    @PatchMapping("/users/{userId}/incomes/{incomeId}/rename")
    public void renameIncome (@PathVariable Long userId, @PathVariable Long incomeId, @RequestBody IncomeRenameRequest request){

        User user = userService.findActiveUser(userId);

        incomeService.renameIncome(incomeId, request.getNewName(), user);
    }

    @PatchMapping("/users/{userId}/incomes/{incomeId}/remove")
    public void removeIncome(@PathVariable Long userId, @PathVariable Long incomeId){

        User user = userService.findActiveUser(userId);

        incomeService.removeIncome(incomeId, user);
    }

    @PatchMapping("/users/{userId}/incomes/{incomeId}/transactions/{transactionId}/amount")
    public Transaction updateIncome(@PathVariable Long userId, @PathVariable Long incomeId, @PathVariable Long transactionId, @RequestBody IncomeAmountRequest request){

        User user = userService.findActiveUser(userId);

        return incomeService.updateIncome(incomeId,transactionId, request.getNewIncomeAmount(),user);
    }

    @PostMapping("/users/{userId}/incomes/{incomeId}/record")
    public void recordIncome(@PathVariable Long userId, @PathVariable Long incomeId, @RequestParam BigDecimal amount){

        User user = userService.findActiveUser(userId);

        incomeService.recordIncome(incomeId, amount, user);
    }
}

