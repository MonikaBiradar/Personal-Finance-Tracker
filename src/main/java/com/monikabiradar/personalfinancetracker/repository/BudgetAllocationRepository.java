package com.monikabiradar.personalfinancetracker.repository;

import com.monikabiradar.personalfinancetracker.entity.Budget;
import com.monikabiradar.personalfinancetracker.entity.BudgetAllocation;
import com.monikabiradar.personalfinancetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BudgetAllocationRepository extends JpaRepository<BudgetAllocation, Long> {

    boolean existsByBudgetBudgetIdAndExpenseExpenseId(Long budgetId, Long expenseId);

    Optional<BudgetAllocation> findByAllocationIdAndBudgetUserUserId(Long allocationId, Long userId);
}
