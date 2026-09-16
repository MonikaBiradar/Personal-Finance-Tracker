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

    public String getSavingsName() {
        return savingsName;
    }

    public void setSavingsName(String savingsName) {
        this.savingsName = savingsName;
    }

    public BigDecimal getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(BigDecimal targetAmount) {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(BigDecimal currentAmount) {
        this.currentAmount = currentAmount;
    }

    public SavingsStatus getSavingsStatus() {
        return savingsStatus;
    }

    public void setSavingsStatus(SavingsStatus savingsStatus) {
        this.savingsStatus = savingsStatus;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getSavingsId() {
        return savingsId;
    }
}
