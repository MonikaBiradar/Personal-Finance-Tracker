package com.monikabiradar.personalfinancetracker.repository;

import com.monikabiradar.personalfinancetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserUserIdAndExpenseNameIgnoreCase (Long userId, String expenseName);

    boolean existsByUserUserIdAndExpenseName(Long userId,String expenseName);

    Optional<Expense> findByExpenseIdAndUserUserId(Long expenseId, Long userId);

    boolean existsByUserUserIdAndExpenseNameAndExpenseIdNot (Long userId, String expenseName, Long expenseId);
}
