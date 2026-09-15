package com.monikabiradar.personalfinancetracker.controller;

import com.monikabiradar.personalfinancetracker.dto.FrequencyUpdateRequest;
import com.monikabiradar.personalfinancetracker.dto.IncomeAmountRequest;
import com.monikabiradar.personalfinancetracker.dto.IncomeRenameRequest;
import com.monikabiradar.personalfinancetracker.dto.IncomeRequest;
import com.monikabiradar.personalfinancetracker.entity.Income;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.exception.UserNotFoundException;
import com.monikabiradar.personalfinancetracker.repository.IncomeRepository;
import com.monikabiradar.personalfinancetracker.repository.UserRepository;
import com.monikabiradar.personalfinancetracker.service.IncomeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class IncomeController {

    private final IncomeService incomeService;
    private final UserRepository userRepository;

    public IncomeController(IncomeService incomeService, UserRepository userRepository) {
        this.incomeService = incomeService;
        this.userRepository = userRepository;
    }

    @PostMapping("/users/{userId}/incomes")
    public Income addIncome(@PathVariable Long userId, @RequestBody IncomeRequest request){
        User user = userRepository
                .findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        return incomeService.addIncome(request, user);
    }

    @GetMapping("/users/{userId}/incomes")
    public List<Income> searchIncome(@PathVariable Long userId, @RequestParam String name){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        return incomeService.searchIncome(name, user);
    }

    @PatchMapping("/users/{userId}/incomes/{incomeId}/frequency")
    public void updateFrequency(@PathVariable Long userId, @PathVariable Long incomeId, @RequestBody FrequencyUpdateRequest request){

        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        incomeService.updateFrequency(incomeId,request.getFrequency(),user);
    }

    @PatchMapping("/users/{userId}/incomes/{incomeId}/rename")
    public void renameIncome (@PathVariable Long userId, @PathVariable Long incomeId, @RequestBody IncomeRenameRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        incomeService.renameIncome(incomeId, request.getNewName(), user);
    }

    @PatchMapping("/users/{userId}/incomes/{incomeId}/remove")
    public void removeIncome(@PathVariable Long userId, @PathVariable Long incomeId){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        incomeService.removeIncome(incomeId, user);
    }

    @PatchMapping("/users/{userId}/incomes/{incomeId}/amount")
    public void updateIncome(@PathVariable Long userId, @PathVariable Long incomeId, @RequestBody IncomeAmountRequest request){
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        incomeService.updateIncome(incomeId,request.getNewIncomeAmount(),user);
    }
}
  
