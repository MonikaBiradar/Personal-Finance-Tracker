package com.monikabiradar.personalfinancetracker.repository;

import com.monikabiradar.personalfinancetracker.entity.Transaction;
import com.monikabiradar.personalfinancetracker.enums.TransactionType;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

    List<Transaction> findByUserUserId(Long userId);

    List<Transaction> findByUserUserIdAndTransactionDate(Long userId, LocalDate transactionDate);

    List<Transaction> findByUserUserIdAndTransactionType(Long userId, TransactionType transactionType);

    List<Transaction> findByUserUserIdAndTransactionTypeAndTransactionDate(Long userId, TransactionType transactionType, LocalDate transactionDate);

    List<Transaction> findByUserUserId(Long userId, Sort sort);

    Optional<Transaction> findByTransactionIdAndUserUserId(Long transactionId, Long userId);

    @Query("""
    SELECT COALESCE(SUM(t.amount), 0)
    FROM Transaction t
    WHERE t.user.userId = :userId
      AND t.expense.expenseId = :expenseId
      AND t.transactionType =
          com.monikabiradar.personalfinancetracker.enums.TransactionType.EXPENSE
      AND t.transactionStatus =
          com.monikabiradar.personalfinancetracker.enums.TransactionStatus.ACTIVE
    """)
    BigDecimal sumActiveExpenseAmount (@Param("userId") Long userId,@Param("expenseId") Long expenseId);
}
