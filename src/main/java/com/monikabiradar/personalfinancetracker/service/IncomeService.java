package com.monikabiradar.personalfinancetracker.service;

import com.monikabiradar.personalfinancetracker.dto.IncomeRequest;
import com.monikabiradar.personalfinancetracker.entity.Income;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.enums.IncomeStatus;
import com.monikabiradar.personalfinancetracker.enums.UpdateFrequency;
import com.monikabiradar.personalfinancetracker.exception.DuplicateIncomeException;
import com.monikabiradar.personalfinancetracker.exception.IncomeNotFoundException;
import com.monikabiradar.personalfinancetracker.repository.IncomeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;

    public IncomeService(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    public Income addIncome(IncomeRequest incomeRequest, User user) {
        Income income = new Income();

        income.setIncomeName(incomeRequest.getIncomeName());
        income.setIncomeAmount(incomeRequest.getIncomeAmount());
        income.setUpdateFrequency(incomeRequest.getUpdateFrequency());
        income.setIncomeStatus(IncomeStatus.ACTIVE);
        income.setLastUpdatedDate(LocalDate.now());
        income.setUser(user);

        return incomeRepository.save(income);
    }

    public Income updateFrequency(Long incomeId, UpdateFrequency newFrequency, User user) {

        Income income = incomeRepository
                .findByIncomeIdAndUserUserId(incomeId,user.getUserId())
                .orElseThrow(()-> new IncomeNotFoundException("Income not found."));

        income.setUpdateFrequency(newFrequency);
        income.setLastUpdatedDate(LocalDate.now());
        return incomeRepository.save(income);
    }

    public List<Income> searchIncome(String incomeName, User user) {

        return incomeRepository.findByUserUserIdAndIncomeNameIgnoreCase(user.getUserId(),incomeName);
    }

    public void renameIncome(Long incomeId, String newIncomeName, User user) {

        Income income = incomeRepository
                .findByIncomeIdAndUserUserId(incomeId,user.getUserId())
                .orElseThrow(()-> new IncomeNotFoundException("Income not found."));

        boolean exists = incomeRepository
                .existsByUserUserIdAndIncomeNameAndIncomeIdNot(user.getUserId(),newIncomeName,incomeId);

        if (exists) {
            throw new DuplicateIncomeException("Income already exists.");
        }

        income.setIncomeName(newIncomeName);
        incomeRepository.save(income);
    }

    public void removeIncome(Long incomeId, User user) {
        Income income = incomeRepository.findByIncomeIdAndUserUserId(incomeId, user.getUserId())
                .orElseThrow(()-> new IncomeNotFoundException("Income not found."));

        income.setIncomeStatus(IncomeStatus.REVERTED);
        incomeRepository.save(income);
    }

    public Income updateIncome(Long incomeId, BigDecimal newIncomeAmount, User user) {
        Income income = incomeRepository.findByIncomeIdAndUserUserId(incomeId, user.getUserId())
                .orElseThrow(()-> new IncomeNotFoundException("Income not found."));

        income.setIncomeAmount(newIncomeAmount);
        return incomeRepository.save(income);
    }
}
