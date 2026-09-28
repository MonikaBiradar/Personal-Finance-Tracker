package com.monikabiradar.personalfinancetracker.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "budget_allocations")
public class BudgetAllocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long allocationId;

    private BigDecimal allocatedAmount;

    @ManyToOne
    @JoinColumn(name = "budget_id")
    private Budget budget;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense;

    public BigDecimal getAllocatedAmount() {
        return allocatedAmount;
    }

    public void setAllocatedAmount(BigDecimal allocatedAmount) {
        this.allocatedAmount = allocatedAmount;
    }

    public Budget getBudget() {
        return budget;
    }

    public void setBudget(Budget budget) {
        this.budget = budget;
    }

    public Expense getExpense() {
        return expense;
    }

    public void setExpense(Expense expense) {
        this.expense = expense;
    }

    public Long getAllocationId() {
        return allocationId;
    }
}
