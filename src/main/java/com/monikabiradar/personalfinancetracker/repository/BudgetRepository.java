package com.monikabiradar.personalfinancetracker.repository;

import com.monikabiradar.personalfinancetracker.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long> {

    boolean existsByUserUserIdAndMonth(Long userId, LocalDate month);

    Optional<Budget> findByBudgetIdAndUserUserId(Long budgetId, Long userId);
}
