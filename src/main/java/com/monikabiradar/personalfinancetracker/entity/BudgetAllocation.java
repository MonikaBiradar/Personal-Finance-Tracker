package com.monikabiradar.personalfinancetracker.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "budgetAllocations")
public class BudgetAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int allocationId;

    private BigDecimal allocatedAmount;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;


}
