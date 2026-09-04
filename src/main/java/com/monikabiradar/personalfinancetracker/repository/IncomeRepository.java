package com.monikabiradar.personalfinancetracker.repository;

import com.monikabiradar.personalfinancetracker.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncomeRepository extends JpaRepository<Income, Long> {
}
