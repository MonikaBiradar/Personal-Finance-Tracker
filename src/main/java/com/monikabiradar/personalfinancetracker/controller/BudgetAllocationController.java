package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.dto.BudgetAllocationRequest;
import com.monikabiradar.personalfinancetracker.dto.BudgetTransferRequest;
import com.monikabiradar.personalfinancetracker.entity.BudgetAllocation;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.service.BudgetAllocationService;
import com.monikabiradar.personalfinancetracker.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BudgetAllocationController {

    private final BudgetAllocationService budgetAllocationService;
    private final UserService userService;

    public BudgetAllocationController(BudgetAllocationService budgetAllocationService, UserService userService) {
        this.budgetAllocationService = budgetAllocationService;
        this.userService = userService;
    }

    @PostMapping("/users/{userId}/budget-allocations")
    public ResponseEntity<BudgetAllocation> addAllocation(@PathVariable Long userId, @RequestBody BudgetAllocationRequest request) {

        User user = userService.findActiveUser(userId);

        BudgetAllocation budgetAllocation = budgetAllocationService.addAllocation(request, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(budgetAllocation);
    }

    @PatchMapping("/users/{userId}/budget-allocations/transfer")
    public ResponseEntity<Void> transferBudget(@PathVariable Long userId, @RequestBody BudgetTransferRequest request) {

        User user = userService.findActiveUser(userId);

        budgetAllocationService.transferAllocation(request.getSourceAllocation(), request.getDestinationAllocation(), request.getAmount(), user);

        return ResponseEntity.ok().build();
    }
}
