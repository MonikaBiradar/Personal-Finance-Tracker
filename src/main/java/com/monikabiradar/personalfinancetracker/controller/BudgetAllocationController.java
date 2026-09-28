package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.dto.BudgetAllocationRequest;
import com.monikabiradar.personalfinancetracker.dto.BudgetTransferRequest;
import com.monikabiradar.personalfinancetracker.entity.BudgetAllocation;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.exception.UserNotFoundException;
import com.monikabiradar.personalfinancetracker.repository.UserRepository;
import com.monikabiradar.personalfinancetracker.service.BudgetAllocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BudgetAllocationController {

    private final BudgetAllocationService budgetAllocationService;
    private final UserRepository userRepository;

    public BudgetAllocationController(BudgetAllocationService budgetAllocationService, UserRepository userRepository) {
        this.budgetAllocationService = budgetAllocationService;
        this.userRepository = userRepository;
    }

    @PostMapping("/users/{userId}/budget-allocations")
    public ResponseEntity<BudgetAllocation> addAllocation(@PathVariable Long userId, @RequestBody BudgetAllocationRequest request) {

        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found"));

        BudgetAllocation budgetAllocation = budgetAllocationService.addAllocation(request, user);

        return ResponseEntity.status(HttpStatus.CREATED).body(budgetAllocation);
    }

    @PatchMapping("/users/{userId}/budget-allocations/transfer")
    public ResponseEntity<Void> transferBudget(@PathVariable Long userId, @RequestBody BudgetTransferRequest request) {

        User user = userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User not found"));

        budgetAllocationService.transferAllocation(request.getSourceAllocation(), request.getDestinationAllocation(), request.getAmount(), user);

        return ResponseEntity.ok().build();
    }
}
