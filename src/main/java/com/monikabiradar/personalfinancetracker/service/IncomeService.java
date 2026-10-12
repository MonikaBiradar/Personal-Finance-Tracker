package com.monikabiradar.personalfinancetracker.service;

import com.monikabiradar.personalfinancetracker.dto.IncomeRequest;
import com.monikabiradar.personalfinancetracker.entity.Income;
import com.monikabiradar.personalfinancetracker.entity.Transaction;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.enums.IncomeStatus;
import com.monikabiradar.personalfinancetracker.enums.TransactionStatus;
import com.monikabiradar.personalfinancetracker.enums.TransactionType;
import com.monikabiradar.personalfinancetracker.enums.UpdateFrequency;
import com.monikabiradar.personalfinancetracker.exception.DuplicateIncomeException;
import com.monikabiradar.personalfinancetracker.exception.IncomeNotFoundException;
import com.monikabiradar.personalfinancetracker.exception.TransactionNotFoundException;
import com.monikabiradar.personalfinancetracker.repository.IncomeRepository;
import com.monikabiradar.personalfinancetracker.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class IncomeService {

    private final IncomeRepository incomeRepository;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    public IncomeService(IncomeRepository incomeRepository,  TransactionService transactionService,  TransactionRepository transactionRepository) {
        this.incomeRepository = incomeRepository;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    public Income addIncome(IncomeRequest incomeRequest, User user) {
        Income income = new Income();

        income.setIncomeName(incomeRequest.getIncomeName());
        income.setUpdateFrequency(incomeRequest.getUpdateFrequency());
        income.setIncomeStatus(IncomeStatus.ACTIVE);
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

    @Transactional
    public Transaction updateIncome(Long incomeId,Long transactionId, BigDecimal newIncomeAmount, User user) {
        Income income = incomeRepository.findByIncomeIdAndUserUserId(incomeId, user.getUserId())
                .orElseThrow(()-> new IncomeNotFoundException("Income not found."));

        if(income.getIncomeStatus() == IncomeStatus.REVERTED) {
            throw new IncomeNotFoundException("Income not found.");
        }

        if(newIncomeAmount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Income amount should be greater than zero");
        }

        Transaction transaction = transactionRepository
                .findByTransactionIdAndIncomeIncomeIdAndUserUserId(transactionId, incomeId, user.getUserId())
                .orElseThrow(()-> new TransactionNotFoundException("Transaction not found"));

        if(transaction.getTransactionType() != TransactionType.INCOME) {
            throw new IllegalArgumentException("Income type should be INCOME");
        }

        if(transaction.getTransactionStatus() == TransactionStatus.REVERTED) {
            throw new TransactionNotFoundException("Transaction not found");
        }

        transaction.setTransactionStatus(TransactionStatus.REVERTED);
        income.setIncomeAmount(newIncomeAmount);

        Transaction newTransaction = transactionService.addTransaction(TransactionType.INCOME, newIncomeAmount, user, income);
        income.setLastUpdatedDate(LocalDate.now());

        incomeRepository.save(income);
        transactionRepository.save(transaction);

        return newTransaction;
    }

    @Transactional
    public Transaction recordIncome(Long incomeId, BigDecimal amount, User user) {
        Income income = incomeRepository
                .findByIncomeIdAndUserUserId(incomeId, user.getUserId())
                .orElseThrow(()-> new IncomeNotFoundException("Income not found."));

        if(income.getIncomeStatus() == IncomeStatus.REVERTED) {
            throw new IncomeNotFoundException("Income status is reverted.");
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than 0.");
        }

        income.setIncomeAmount(amount);

        Transaction transaction = transactionService.addTransaction(TransactionType.INCOME, amount, user, income);

        income.setLastUpdatedDate(LocalDate.now());

        incomeRepository.save(income);

        return transaction;
    }
}
