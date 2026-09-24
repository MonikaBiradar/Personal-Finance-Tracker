package com.monikabiradar.personalfinancetracker.specification;

import com.monikabiradar.personalfinancetracker.entity.Transaction;
import com.monikabiradar.personalfinancetracker.entity.User;
import com.monikabiradar.personalfinancetracker.enums.TransactionStatus;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransactionSpecification implements Specification<Transaction> {

    private final User user;
    private final BigDecimal minAmount;
    private final BigDecimal maxAmount;
    private final TransactionStatus status;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public TransactionSpecification(User user,BigDecimal minAmount,BigDecimal maxAmount, TransactionStatus status, LocalDate startDate, LocalDate endDate){
        this.user = user;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
    }


    @Override
    public @Nullable Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        Predicate userPredicate = criteriaBuilder.equal(root.get("user"), user);
        predicates.add(userPredicate);
        if(minAmount != null){
            Predicate minAmountPredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), minAmount);
            predicates.add(minAmountPredicate);
        }
        if(maxAmount != null){
            Predicate  maxAmountPredicate = criteriaBuilder.lessThanOrEqualTo(root.get("amount"), maxAmount);
            predicates.add(maxAmountPredicate);
        }
        if(status != null){
            Predicate statusPredicate = criteriaBuilder.equal(root.get("transactionStatus"), status);
            predicates.add(statusPredicate);
        }
        if(startDate != null){
            Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(root.get("transactionDate"), startDate);
            predicates.add(startDatePredicate);
        }
        if(endDate != null){
            Predicate endDatePredicate = criteriaBuilder.lessThanOrEqualTo(root.get("transactionDate"), endDate);
            predicates.add(endDatePredicate);
        }
        Predicate combinedPredicate = criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        return combinedPredicate;
    }
}
