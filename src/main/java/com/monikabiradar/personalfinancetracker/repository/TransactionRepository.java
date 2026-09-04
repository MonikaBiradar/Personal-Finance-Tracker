package com.monikabiradar.personalfinancetracker.repository;

import com.monikabiradar.personalfinancetracker.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
