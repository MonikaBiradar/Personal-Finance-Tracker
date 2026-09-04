package com.monikabiradar.personalfinancetracker.repository;

import com.monikabiradar.personalfinancetracker.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
