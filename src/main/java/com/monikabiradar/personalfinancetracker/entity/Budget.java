package com.monikabiradar.personalfinancetracker.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int budgetId;

    private BigDecimal totalBudget;

    private LocalDate month;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
