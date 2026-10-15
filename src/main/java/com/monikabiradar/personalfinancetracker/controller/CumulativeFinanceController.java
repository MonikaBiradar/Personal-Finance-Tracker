package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.service.CumulativeFinanceService;
import com.monikabiradar.personalfinancetracker.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class CumulativeFinanceController {

    private final CumulativeFinanceService cumulativeFinanceService;
    private final UserService userService;

    public CumulativeFinanceController(CumulativeFinanceService cumulativeFinanceService, UserService userService) {
        this.cumulativeFinanceService = cumulativeFinanceService;
        this.userService = userService;
    }

    @GetMapping("/users/{userId}/cumulative-finance")
    public BigDecimal getCumulativeFinance(@PathVariable Long userId) {

        User user = userService.findActiveUser(userId);
        return cumulativeFinanceService.getCurrentSavingsBalance(user.getUserId());
    }
}
