package com.monikabiradar.personalfinancetracker.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "savings")
public class Savings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long savingsId;

    private String savingsName;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
