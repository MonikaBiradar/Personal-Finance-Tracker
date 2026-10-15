package com.monikabiradar.personalfinancetracker.service;

import com.monikabiradar.personalfinancetracker.enums.TransactionType;
import com.monikabiradar.personalfinancetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class MonthlyFinanceService {

    private final TransactionRepository transactionRepository;

    public MonthlyFinanceService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getMonthlyIncome(Long userId, int year, int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        return transactionRepository.sumActiveAmountByUserAndTypeAndDateRange(userId, TransactionType.INCOME, startDate, endDate);
    }

    public BigDecimal getMonthlyExpenses(Long userId, int year, int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        return transactionRepository.sumActiveAmountByUserAndTypeAndDateRange(userId, TransactionType.EXPENSE, startDate, endDate);
    }

    public BigDecimal getMonthlySavings(Long userId, int year, int month) {

        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.plusMonths(1);

        return transactionRepository.sumActiveAmountByUserAndTypeAndDateRange(userId, TransactionType.SAVINGS_ALLOCATION, startDate, endDate);
    }
}
