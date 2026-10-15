package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.dto.MonthlyFinanceResponse;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.service.MonthlyFinanceService;
import com.monikabiradar.personalfinancetracker.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.YearMonth;

@RestController
public class MonthlyFinanceController {

    private final MonthlyFinanceService monthlyFinanceService;
    private final UserService userService;

    public MonthlyFinanceController(MonthlyFinanceService monthlyFinanceService, UserService userService) {

        this.monthlyFinanceService = monthlyFinanceService;
        this.userService = userService;
    }

    @GetMapping("/users/{userId}/monthly-finance")
    public MonthlyFinanceResponse getMonthlyFinance(@PathVariable Long userId, @RequestParam Integer year, @RequestParam Integer month) {

        User user = userService.findActiveUser(userId);
        YearMonth selectedMonth = YearMonth.of(year, month);

        return new MonthlyFinanceResponse(monthlyFinanceService.getMonthlyIncome(user.getUserId(), selectedMonth.getYear(), selectedMonth.getMonthValue()),
                                          monthlyFinanceService.getMonthlyExpenses(user.getUserId(), selectedMonth.getYear(), selectedMonth.getMonthValue()),
                                          monthlyFinanceService.getMonthlySavings(user.getUserId(), selectedMonth.getYear(), selectedMonth.getMonthValue()));
    }
}
