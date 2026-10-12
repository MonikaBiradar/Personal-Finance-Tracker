package com.monikabiradar.personalfinancetracker.service;

import com.monikabiradar.personalfinancetracker.entity.*;
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
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final BudgetRepository budgetRepository;
    private final TransactionRepository transactionRepository;
    private final BudgetAllocationRepository budgetAllocationRepository;
    private final TransactionService transactionService;

    public ExpenseService(ExpenseRepository expenseRepository,
                          BudgetRepository budgetRepository,
                          TransactionRepository transactionRepository,
                          BudgetAllocationRepository budgetAllocationRepository,
                          TransactionService transactionService) {

        this.expenseRepository = expenseRepository;
        this.budgetRepository = budgetRepository;
        this.transactionRepository = transactionRepository;
        this.budgetAllocationRepository = budgetAllocationRepository;
        this.transactionService = transactionService;
    }

    public Expense addExpense(String expenseName, User user){

        boolean exists = expenseRepository
                .existsByUserUserIdAndExpenseName(user.getUserId(), expenseName);

        if (exists) {
            throw new DuplicateExpenseException("Expense already exists");
        }

        Expense expense = new Expense();

        expense.setExpenseName(expenseName);
        expense.setExpenseStatus(ExpenseStatus.ACTIVE);
        expense.setUser(user);

        return expenseRepository.save(expense);
    }

    public List<Expense> searchExpense(String expenseName, User user){
        return expenseRepository
                .findByUserUserIdAndExpenseNameIgnoreCase(user.getUserId(), expenseName);
    }

    public List<Expense> displayExpense(User user){
        return expenseRepository.findByUserUserId(user.getUserId());
    }

    public void renameExpense(Long expenseId, String newName, User user){

        Expense expense = expenseRepository
                .findByExpenseIdAndUserUserId(expenseId, user.getUserId())
                .orElseThrow(()-> new ExpenseNotFoundException("Expense "+expenseId+" Not Found"));

        boolean exists = expenseRepository
                .existsByUserUserIdAndExpenseNameAndExpenseIdNot(user.getUserId(), newName, expenseId);

        if (exists) {
            throw new DuplicateExpenseException("Expense already exists.");
        }

        expense.setExpenseName(newName);
        expenseRepository.save(expense);
    }

    public void removeExpense(Long expenseId, User user){
        Expense expense = expenseRepository
                .findByExpenseIdAndUserUserId(expenseId, user.getUserId())
                .orElseThrow(()-> new ExpenseNotFoundException("Expense "+expenseId+" Not Found"));

        expense.setExpenseStatus(ExpenseStatus.INACTIVE);
        expenseRepository.save(expense);
    }

    @Transactional
    public Transaction recordExpense(Long expenseId, BigDecimal amount, User user){
        Expense expense = expenseRepository
                .findByExpenseIdAndUserUserId(expenseId, user.getUserId())
                .orElseThrow(()-> new ExpenseNotFoundException("Expense "+expenseId+" Not Found"));

        if(amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new IllegalArgumentException("Amount must be greater than 0");
        }

        if(expense.getExpenseStatus() == ExpenseStatus.INACTIVE){
            throw new ExpenseNotFoundException("Expense "+expenseId+" Not Found");
        }

        LocalDate today = LocalDate.now();
        LocalDate budgetMonth = today.withDayOfMonth(1);
        LocalDate nextMonthStartDate = budgetMonth.plusMonths(1);

        Budget budget = budgetRepository
                .findByUserUserIdAndMonth(user.getUserId(), budgetMonth)
                .orElseThrow(()-> new BudgetNotFoundException("Budget Not Found"));

        if(budget.getBudgetStatus() == BudgetStatus.INACTIVE){
            throw new  BudgetNotFoundException("Budget Not Found");
        }

        BigDecimal spentAmount = transactionRepository.sumActiveExpenseAmount(user.getUserId(), expenseId, budgetMonth, nextMonthStartDate);

        BudgetAllocation allocation = budgetAllocationRepository
                .findByBudgetBudgetIdAndExpenseExpenseId(budget.getBudgetId(), expenseId)
                .orElseThrow(()-> new BudgetAllocationNotFoundException("Budget Allocation Not Found"));

        BigDecimal remainingAmount = allocation.getAllocatedAmount().subtract(spentAmount);

        if(remainingAmount.compareTo(amount) < 0){
            throw new InsufficientBudgetException("Budget Insufficient");
        }

        return transactionService.addTransaction(TransactionType.EXPENSE, amount, user, expense);
    }
}
