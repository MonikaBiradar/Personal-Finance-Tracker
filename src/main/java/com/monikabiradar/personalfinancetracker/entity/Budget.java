package com.monikabiradar.personalfinancetracker.entity;

import com.monikabiradar.personalfinancetracker.enums.BudgetStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long budgetId;

    private BigDecimal totalBudget;

    private LocalDate month;

    @Enumerated(EnumType.STRING)
    private BudgetStatus budgetStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getBudgetId() {
        return budgetId;
    }

    public BudgetStatus getBudgetStatus() {
        return budgetStatus;
    }

    public void setBudgetStatus(BudgetStatus budgetStatus) {
        this.budgetStatus = budgetStatus;
    }
}
