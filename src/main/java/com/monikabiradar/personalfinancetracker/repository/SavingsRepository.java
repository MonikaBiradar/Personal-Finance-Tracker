package com.monikabiradar.personalfinancetracker.repository;

import com.monikabiradar.personalfinancetracker.entity.Savings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingsRepository extends JpaRepository<Savings, Long> {
}
