package com.monikabiradar.personalfinancetracker.service;

import com.monikabiradar.personalfinancetracker.enums.TransactionType;
import com.monikabiradar.personalfinancetracker.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class YearlyFinanceService {

    private final TransactionRepository transactionRepository;

    public YearlyFinanceService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public BigDecimal getYearlyIncome(Long userId, int year) {

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = startDate.plusYears(1);

        return transactionRepository.sumActiveAmountByUserAndTypeAndDateRange(userId, TransactionType.INCOME, startDate, endDate);
    }

    public BigDecimal getYearlyExpenses(Long userId, int year) {

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = startDate.plusYears(1);

        return transactionRepository.sumActiveAmountByUserAndTypeAndDateRange(userId, TransactionType.EXPENSE, startDate, endDate);
    }

    public BigDecimal getYearlySavings(Long userId, int year) {

        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = startDate.plusYears(1);

        return transactionRepository.sumActiveAmountByUserAndTypeAndDateRange(userId, TransactionType.SAVINGS_ALLOCATION, startDate, endDate);
    }
}
