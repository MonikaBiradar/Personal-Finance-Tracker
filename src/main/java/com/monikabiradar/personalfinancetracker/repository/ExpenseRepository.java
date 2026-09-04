package com.monikabiradar.personalfinancetracker.repository;

import com.monikabiradar.personalfinancetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
