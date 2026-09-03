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

}
