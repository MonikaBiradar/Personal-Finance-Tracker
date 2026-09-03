package com.monikabiradar.personalfinancetracker.entity;

import com.monikabiradar.personalfinancetracker.enums.IncomeStatus;
import com.monikabiradar.personalfinancetracker.enums.UpdateFrequency;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "incomes")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incomeId;

    private String incomeName;

    private BigDecimal incomeAmount;

    @Enumerated(EnumType.STRING)
    private IncomeStatus incomeStatus;

    @Enumerated(EnumType.STRING)
    private UpdateFrequency updateFrequency;

    private LocalDate lastUpdatedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
