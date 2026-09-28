package com.monikabiradar.personalfinancetracker.service;

import com.monikabiradar.personalfinancetracker.dto.BudgetRequest;
import com.monikabiradar.personalfinancetracker.entity.Budget;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.enums.BudgetStatus;
import com.monikabiradar.personalfinancetracker.exception.BudgetExistsException;
import com.monikabiradar.personalfinancetracker.exception.BudgetNotFoundException;
import com.monikabiradar.personalfinancetracker.repository.BudgetRepository;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {
    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public Budget addBudget(BudgetRequest budgetRequest, User user){
        boolean exists = budgetRepository
                .existsByUserUserIdAndMonth(user.getUserId(), budgetRequest.getMonth());

        if(exists){
            throw new BudgetExistsException("Budget Already Exists");
        }

        Budget budget = new Budget();

        budget.setTotalBudget(budgetRequest.getTotalBudget());
        budget.setMonth(budgetRequest.getMonth());
        budget.setBudgetStatus(BudgetStatus.ACTIVE);
        budget.setUser(user);

        return budgetRepository.save(budget);
    }

    public Budget removeBudget(Long budgetId, User user){
        Budget budget = budgetRepository
                .findByBudgetIdAndUserUserId(budgetId, user.getUserId())
                .orElseThrow(()-> new BudgetNotFoundException("Budget Not Found"));

        budget.setBudgetStatus(BudgetStatus.INACTIVE);
        return budgetRepository.save(budget);
    }
}
