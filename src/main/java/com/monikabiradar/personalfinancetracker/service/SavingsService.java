package com.monikabiradar.personalfinancetracker.service;

import com.monikabiradar.personalfinancetracker.dto.SavingsRequest;
import com.monikabiradar.personalfinancetracker.entity.Expense;
import com.monikabiradar.personalfinancetracker.entity.Savings;
import com.monikabiradar.personalfinancetracker.entity.Transaction;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.enums.ExpenseStatus;
import com.monikabiradar.personalfinancetracker.enums.SavingsStatus;
import com.monikabiradar.personalfinancetracker.enums.TransactionStatus;
import com.monikabiradar.personalfinancetracker.enums.TransactionType;
import com.monikabiradar.personalfinancetracker.exception.DuplicateSavingsException;
import com.monikabiradar.personalfinancetracker.exception.ExpenseNotFoundException;
import com.monikabiradar.personalfinancetracker.exception.SavingsNotFoundException;
import com.monikabiradar.personalfinancetracker.exception.TransactionNotFoundException;
import com.monikabiradar.personalfinancetracker.repository.ExpenseRepository;
import com.monikabiradar.personalfinancetracker.repository.SavingsRepository;
import com.monikabiradar.personalfinancetracker.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SavingsService {

    private final SavingsRepository savingsRepository;
    private final ExpenseRepository expenseRepository;
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    public SavingsService(SavingsRepository savingsRepository,
                          ExpenseRepository expenseRepository,
                          TransactionService transactionService,
                          TransactionRepository transactionRepository) {
        this.savingsRepository = savingsRepository;
        this.expenseRepository = expenseRepository;
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    public Savings addSavings(SavingsRequest request, User user) {
        Savings savings = new Savings();

        savings.setSavingsName(request.getSavingsName());
        savings.setTargetAmount(request.getTargetAmount());
        savings.setSavingsStatus(SavingsStatus.ACTIVE);
        savings.setUser(user);

        return savingsRepository.save(savings);
    }

    public Savings updateTargetSavings(Long savingsId, BigDecimal newTargetAmount, User user) {

        Savings savings = savingsRepository
                .findBySavingsIdAndUserUserId(savingsId, user.getUserId())
                .orElseThrow(()-> new SavingsNotFoundException("Savings not found"));

        savings.setTargetAmount(newTargetAmount);
        return savingsRepository.save(savings);
    }

    public Savings updateCurrentSavings(Long savingsId, BigDecimal newCurrentAmount, User user) {
        Savings savings = savingsRepository
                .findBySavingsIdAndUserUserId(savingsId, user.getUserId())
                .orElseThrow(()-> new SavingsNotFoundException("Savings not found"));

        savings.setCurrentAmount(newCurrentAmount);
        return savingsRepository.save(savings);
    }

    public List<Savings> searchSavings(String savingsName, User user) {
        return savingsRepository.findByUserUserIdAndSavingsNameIgnoreCase(user.getUserId(), savingsName);
    }

    public List<Savings> displaySavings(User user) {
        return savingsRepository.findByUserUserId(user.getUserId());
    }

    public void renameSavings(Long savingsId, String newSavingsName, User user) {
        Savings savings = savingsRepository
                .findBySavingsIdAndUserUserId(savingsId, user.getUserId())
                .orElseThrow(()-> new SavingsNotFoundException("Savings not found"));

        boolean exists = savingsRepository.existsByUserUserIdAndSavingsNameAndSavingsIdNot(user.getUserId(), newSavingsName, savingsId);

        if(exists) {
            throw new DuplicateSavingsException("Savings already exists");
        }
        savings.setSavingsName(newSavingsName);
        savingsRepository.save(savings);
    }

    public void removeSavings(Long savingsId, User user) {
        Savings savings = savingsRepository
                .findBySavingsIdAndUserUserId(savingsId, user.getUserId())
                .orElseThrow(()-> new SavingsNotFoundException("Savings not found"));

        savings.setSavingsStatus(SavingsStatus.INACTIVE);
        savingsRepository.save(savings);
    }

    @Transactional
    public Transaction allocateSavings(Long savingsId,BigDecimal amount, User user) {

        Savings savings = savingsRepository
                .findBySavingsIdAndUserUserId(savingsId, user.getUserId())
                .orElseThrow(()-> new SavingsNotFoundException("Savings not found"));

        if(savings.getSavingsStatus() == SavingsStatus.INACTIVE) {
            throw new SavingsNotFoundException("Savings not found");
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        BigDecimal currentAmount = savings.getCurrentAmount();
        if(currentAmount == null) {
            currentAmount = BigDecimal.ZERO;
        }

        currentAmount = currentAmount.add(amount);

        if(currentAmount.compareTo(savings.getTargetAmount()) > 0) {
            throw new IllegalArgumentException("Current amount is greater than Target amount.");
        }

        savings.setCurrentAmount(currentAmount);
        Transaction newTransaction = transactionService.addTransaction(TransactionType.SAVINGS_ALLOCATION, amount, user, savings);

        savingsRepository.save(savings);
        return newTransaction;
    }

    @Transactional
    public Transaction recordExpenseFromSavings(Long expenseId, Long savingsId, BigDecimal amount, User user) {

        Savings savings = savingsRepository
                .findBySavingsIdAndUserUserId(savingsId, user.getUserId())
                .orElseThrow(()-> new SavingsNotFoundException("Savings not found"));

        if(savings.getSavingsStatus() == SavingsStatus.INACTIVE) {
            throw new SavingsNotFoundException("Savings not found");
        }

        Expense expense = expenseRepository
                .findByExpenseIdAndUserUserId(expenseId, user.getUserId())
                .orElseThrow(()-> new ExpenseNotFoundException("Expense not found"));

        if(expense.getExpenseStatus() == ExpenseStatus.INACTIVE) {
            throw new  ExpenseNotFoundException("Expense not found");
        }

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        BigDecimal currentAmount = savings.getCurrentAmount();
        if(currentAmount == null) {
            currentAmount = BigDecimal.ZERO;
        }

        if(amount.compareTo(currentAmount) > 0) {
            throw new  IllegalArgumentException("amount is greater than current amount.");
        }

        currentAmount = currentAmount.subtract(amount);
        savings.setCurrentAmount(currentAmount);

        Transaction newTransaction = transactionService.addTransaction(TransactionType.EXPENSE, amount, user, expense, savings);

        savingsRepository.save(savings);

        return newTransaction;
    }

    @Transactional
     public Transaction revertSavingsTransaction(Long transactionId, User user) {

        Transaction transaction = transactionRepository.
                findByTransactionIdAndUserUserId(transactionId, user.getUserId()).
                orElseThrow(()-> new TransactionNotFoundException("Transaction not found"));

        if(transaction.getTransactionStatus() == TransactionStatus.REVERTED) {
            throw new TransactionNotFoundException("Transaction not found");
        }

        if(transaction.getTransactionType() == TransactionType.SAVINGS_ALLOCATION) {

            Savings savings = transaction.getSavings();

            if(savings == null) {
                throw new SavingsNotFoundException("Savings not found");
            }

            if(savings.getSavingsStatus() == SavingsStatus.INACTIVE) {
                throw new SavingsNotFoundException("Savings not found");
            }
            if(savings.getCurrentAmount().compareTo(transaction.getAmount()) < 0) {
                throw new IllegalArgumentException("Savings amount is less than transaction amount");
            }

            savings.setCurrentAmount(savings.getCurrentAmount().subtract(transaction.getAmount()));
            savingsRepository.save(savings);
            transaction.setTransactionStatus(TransactionStatus.REVERTED);
            return transactionRepository.save(transaction);
        }
        else if (transaction.getTransactionType() == TransactionType.EXPENSE ) {

            Savings savings = transaction.getSavings();

            if(savings == null) {
                throw new SavingsNotFoundException("Savings not found");
            }

            if(savings.getSavingsStatus() == SavingsStatus.INACTIVE) {
                throw new SavingsNotFoundException("Savings not found");
            }
            if(savings.getCurrentAmount() == null) {
                throw new IllegalArgumentException("Current amount is null");
            }

            savings.setCurrentAmount(savings.getCurrentAmount().add(transaction.getAmount()));
            savingsRepository.save(savings);
            transaction.setTransactionStatus(TransactionStatus.REVERTED);
            return transactionRepository.save(transaction);
        }
        else {
            throw new TransactionNotFoundException("Transaction not found");
        }
     }
}
