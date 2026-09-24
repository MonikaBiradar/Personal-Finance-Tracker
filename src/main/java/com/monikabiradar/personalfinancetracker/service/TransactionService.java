package com.monikabiradar.personalfinancetracker.service;

import com.monikabiradar.personalfinancetracker.entity.Transaction;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.enums.TransactionStatus;
import com.monikabiradar.personalfinancetracker.enums.TransactionType;
import com.monikabiradar.personalfinancetracker.exception.TransactionNotFoundException;
import com.monikabiradar.personalfinancetracker.repository.TransactionRepository;
import com.monikabiradar.personalfinancetracker.specification.TransactionSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction addTransaction(TransactionType transactionType, BigDecimal amount, User user) {

        Transaction transaction = new Transaction();

        transaction.setAmount(amount);
        transaction.setTransactionType(transactionType);
        transaction.setTransactionStatus(TransactionStatus.ACTIVE);
        transaction.setTransactionDate(LocalDate.now());
        transaction.setUser(user);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> displayTransaction(User user){
         return transactionRepository.findByUserUserId(user.getUserId());
    }

    public List<Transaction> searchTransaction(LocalDate date,TransactionType type, User user){
        if (date != null && type == null){
            return transactionRepository.findByUserUserIdAndTransactionDate(user.getUserId(), date);

        } else if (date == null && type != null){
            return transactionRepository.findByUserUserIdAndTransactionType(user.getUserId(), type);

        } else if (date != null && type != null){
            return transactionRepository.findByUserUserIdAndTransactionTypeAndTransactionDate(user.getUserId(), type, date);

        } else{
            throw new IllegalArgumentException("No search criteria provided");
        }
    }

    public List<Transaction> filterTransaction(User user, BigDecimal minAmount, BigDecimal maxAmount, TransactionStatus status, LocalDate startDate, LocalDate endDate){
        TransactionSpecification specification = new TransactionSpecification(user, minAmount, maxAmount, status, startDate, endDate);
        return transactionRepository.findAll(specification);
    }

    public List<Transaction> sortTransaction(User user, String sortBy, String order){
        Sort sort;

        if(sortBy.equals("date")){
            if(order.equals("latest")){
                sort = Sort.by("transactionDate").descending();
            }else if(order.equals("oldest")){
                sort = Sort.by("transactionDate").ascending();
            }else{
                throw new IllegalArgumentException("Invalid date sort order");
            }
        }else if(sortBy.equals("amount")){
            if(order.equals("highest")){
                sort = Sort.by("amount").descending();
            }else if(order.equals("lowest")){
                sort = Sort.by("amount").ascending();
            }else {
                throw new IllegalArgumentException("Invalid amount sort order");
            }
        }else {
            throw new IllegalArgumentException("Invalid sort field");
        }

        return transactionRepository.findByUserUserId(user.getUserId(), sort);
    }

    public Transaction retractTransaction(Long transactionId, User user){
        Transaction transaction = transactionRepository
                .findByTransactionIdAndUserUserId(transactionId, user.getUserId())
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        transaction.setTransactionStatus(TransactionStatus.REVERTED);
        return transactionRepository.save(transaction);
    }
}
