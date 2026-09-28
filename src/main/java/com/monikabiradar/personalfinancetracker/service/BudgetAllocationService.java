package com.monikabiradar.personalfinancetracker.service;

import com.monikabiradar.personalfinancetracker.dto.BudgetAllocationRequest;
import com.monikabiradar.personalfinancetracker.entity.Budget;
import com.monikabiradar.personalfinancetracker.entity.BudgetAllocation;
import com.monikabiradar.personalfinancetracker.entity.Expense;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.enums.BudgetStatus;
import com.monikabiradar.personalfinancetracker.enums.ExpenseStatus;
import com.monikabiradar.personalfinancetracker.enums.TransactionType;
import com.monikabiradar.personalfinancetracker.exception.*;
import com.monikabiradar.personalfinancetracker.repository.BudgetAllocationRepository;
import com.monikabiradar.personalfinancetracker.repository.BudgetRepository;
import com.monikabiradar.personalfinancetracker.repository.ExpenseRepository;
import com.monikabiradar.personalfinancetracker.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BudgetAllocationService {

    private final BudgetAllocationRepository budgetAllocationRepository;
    private final BudgetRepository budgetRepository;
    private final ExpenseRepository expenseRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;

    public BudgetAllocationService(BudgetAllocationRepository budgetAllocationRepository,
                                   BudgetRepository budgetRepository,
                                   ExpenseRepository expenseRepository,
                                   TransactionRepository transactionRepository,
                                   TransactionService transactionService) {
        this.budgetAllocationRepository = budgetAllocationRepository;
        this.budgetRepository = budgetRepository;
        this.expenseRepository = expenseRepository;
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
    }

    public BudgetAllocation addAllocation(BudgetAllocationRequest request, User user) {

        Budget budget = budgetRepository
                .findByBudgetIdAndUserUserId(request.getBudgetId(), user.getUserId())
                .orElseThrow(()-> new BudgetNotFoundException("Budget Not Found."));

        if (budget.getBudgetStatus() == BudgetStatus.INACTIVE) {
            throw new BudgetNotFoundException("Budget Not Found.");
        }

        Expense expense = expenseRepository
                .findByExpenseIdAndUserUserId(request.getExpenseId(), user.getUserId())
                .orElseThrow(()-> new ExpenseNotFoundException("Expense Not Found."));

        if(expense.getExpenseStatus() == ExpenseStatus.INACTIVE) {
            throw new ExpenseNotFoundException("Expense Not Found.");
        }

        boolean exists = budgetAllocationRepository.existsByBudgetBudgetIdAndExpenseExpenseId(request.getBudgetId(), request.getExpenseId());
        if(exists){
            throw new DuplicateAllocationException("Budget Allocation already exists.");
        }

        BudgetAllocation budgetAllocation = new BudgetAllocation();

        budgetAllocation.setAllocatedAmount(request.getAllocatedAmount());
        budgetAllocation.setBudget(budget);
        budgetAllocation.setExpense(expense);

        return budgetAllocationRepository.save(budgetAllocation);
    }

    @Transactional
    public void transferAllocation(Long sourceAllocation,Long destinationAllocation, BigDecimal amount, User user){
        BudgetAllocation source = budgetAllocationRepository
                .findByAllocationIdAndBudgetUserUserId(sourceAllocation,user.getUserId())
                .orElseThrow(()-> new BudgetAllocationNotFoundException("Allocation Not Found."));

        BudgetAllocation destination = budgetAllocationRepository
                .findByAllocationIdAndBudgetUserUserId(destinationAllocation, user.getUserId())
                .orElseThrow(()-> new BudgetAllocationNotFoundException("Allocation Not Found."));

        if(!source.getBudget().getBudgetId().equals(destination.getBudget().getBudgetId())){
            throw new AllocationConflictException("Allocations must belong to same budget.");
        }

        if(source.getAllocationId().equals(destination.getAllocationId())){
            throw new IllegalArgumentException("Source and Destination Allocations must be different.");
        }

        if(source.getExpense().getExpenseStatus() == ExpenseStatus.INACTIVE || destination.getExpense().getExpenseStatus() == ExpenseStatus.INACTIVE){
            throw new ExpenseNotFoundException("Expense Not Found.");
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        BigDecimal spentAmount = transactionRepository.sumActiveExpenseAmount(user.getUserId(), source.getExpense().getExpenseId());

        BigDecimal remainingAmount = source.getAllocatedAmount().subtract(spentAmount);

        if(amount.compareTo(remainingAmount) > 0){
            throw new InsufficientBudgetException("Insufficient Balance.");
        }

        source.setAllocatedAmount(source.getAllocatedAmount().subtract(amount));
        destination.setAllocatedAmount(destination.getAllocatedAmount().add(amount));

        budgetAllocationRepository.save(source);
        budgetAllocationRepository.save(destination);

        transactionService.addTransaction(TransactionType.BUDGET_TRANSFER, amount, user);
    }
}
