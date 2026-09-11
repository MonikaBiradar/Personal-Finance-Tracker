package com.monikabiradar.personalfinancetracker.entity;

import com.monikabiradar.personalfinancetracker.enums.ExpenseStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name= "expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long expenseId;

    private String expenseName;

    @Enumerated(EnumType.STRING)
    private ExpenseStatus  expenseStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getExpenseId() {
        return expenseId;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public ExpenseStatus getExpenseStatus() {
        return expenseStatus;
    }

    public void setExpenseStatus(ExpenseStatus expenseStatus) {
        this.expenseStatus = expenseStatus;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
