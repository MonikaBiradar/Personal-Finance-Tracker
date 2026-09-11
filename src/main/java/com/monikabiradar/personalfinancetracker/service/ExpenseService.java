package com.monikabiradar.personalfinancetracker.service;

import com.monikabiradar.personalfinancetracker.entity.Expense;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.enums.ExpenseStatus;
import com.monikabiradar.personalfinancetracker.exception.DuplicateExpenseException;
import com.monikabiradar.personalfinancetracker.exception.ExpenseNotFoundException;
import com.monikabiradar.personalfinancetracker.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {

        this.expenseRepository = expenseRepository;
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



}
