package com.monikabiradar.personalfinancetracker.entity;

import com.monikabiradar.personalfinancetracker.enums.SavingsStatus;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "savings")
public class Savings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingsId;

    private String savingsName;

    private BigDecimal targetAmount;

    private BigDecimal currentAmount;

    @Enumerated(EnumType.STRING)
    private SavingsStatus savingsStatus;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
