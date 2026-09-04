package com.monikabiradar.personalfinancetracker.repository;

import com.monikabiradar.personalfinancetracker.entity.BudgetAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetAllocationRepository extends JpaRepository<BudgetAllocation, Long> {
}
