package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.dto.CurrentSavingsAmountRequest;
import com.monikabiradar.personalfinancetracker.dto.NewTargetAmountRequest;
import com.monikabiradar.personalfinancetracker.dto.SavingsRenameRequest;
import com.monikabiradar.personalfinancetracker.dto.SavingsRequest;
import com.monikabiradar.personalfinancetracker.entity.Savings;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.service.SavingsService;
import com.monikabiradar.personalfinancetracker.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SavingsController {

    private final SavingsService savingsService;
    private final UserService userService;

    public SavingsController(SavingsService savingsService, UserService userService) {
        this.savingsService = savingsService;
        this.userService = userService;
    }

    @PostMapping("/users/{userId}/savings")
    public Savings addSavings(@PathVariable Long userId, @RequestBody SavingsRequest request) {
        User user = userService.findActiveUser(userId);

        return savingsService.addSavings(request, user);
    }

    @PatchMapping("/users/{userId}/savings/{savingsId}/target")
    public void updateTargetSavings(@PathVariable Long userId, @PathVariable Long savingsId, @RequestBody NewTargetAmountRequest request) {
        User user = userService.findActiveUser(userId);

        savingsService.updateTargetSavings(savingsId, request.getNewTargetAmount(), user);
    }

    @PatchMapping("/users/{userId}/savings/{savingsId}/current")
    public void updateCurrentSavings(@PathVariable Long userId, @PathVariable Long savingsId, @RequestBody CurrentSavingsAmountRequest request) {
        User user = userService.findActiveUser(userId);

        savingsService.updateCurrentSavings(savingsId, request.getNewCurrentAmount(), user);
    }

    @GetMapping("/users/{userId}/savings")
    public List<Savings> searchSavings(@PathVariable Long userId, @RequestParam String name) {

        User user = userService.findActiveUser(userId);

        return savingsService.searchSavings(name, user);
    }

    @PatchMapping("/users/{userId}/savings/{savingsId}/rename")
    public void renameSavings(@PathVariable Long userId, @PathVariable Long savingsId, @RequestBody SavingsRenameRequest request) {

        User user = userService.findActiveUser(userId);

        savingsService.renameSavings(savingsId, request.getNewSavingsName(), user);
    }

    @PatchMapping("/users/{userId}/savings/{savingsId}/remove")
    public void removeSavings(@PathVariable Long userId, @PathVariable Long savingsId) {

        User user = userService.findActiveUser(userId);

        savingsService.removeSavings(savingsId, user);
    }
}
