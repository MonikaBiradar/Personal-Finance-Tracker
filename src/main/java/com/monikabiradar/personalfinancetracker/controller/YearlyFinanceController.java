package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.dto.YearlyFinanceResponse;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.service.UserService;
import com.monikabiradar.personalfinancetracker.service.YearlyFinanceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;

@RestController
public class YearlyFinanceController {

    private final YearlyFinanceService yearlyFinanceService;
    private final UserService userService;

    public YearlyFinanceController(YearlyFinanceService yearlyFinanceService,  UserService userService) {
        this.yearlyFinanceService = yearlyFinanceService;
        this.userService = userService;
    }

    @GetMapping("/users/{userId}/yearly-finance")
    public YearlyFinanceResponse getYearlyFinance(@PathVariable Long userId, @RequestParam int year) {

        User user = userService.findActiveUser(userId);

        return new YearlyFinanceResponse(yearlyFinanceService.getYearlyIncome(user.getUserId(), year),
                                         yearlyFinanceService.getYearlyExpenses(user.getUserId(), year),
                                         yearlyFinanceService.getYearlySavings(user.getUserId(), year));

    }
}
