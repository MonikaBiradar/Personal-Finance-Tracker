package com.monikabiradar.personalfinancetracker.service;

import com.monikabiradar.personalfinancetracker.repository.SavingsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CumulativeFinanceService {

    private final SavingsRepository savingsRepository;

    public CumulativeFinanceService(SavingsRepository savingsRepository) {
        this.savingsRepository = savingsRepository;
    }

    public BigDecimal getCurrentSavingsBalance(Long userId) {

        return savingsRepository.sumActiveSavingsByUserId(userId);
    }
}
