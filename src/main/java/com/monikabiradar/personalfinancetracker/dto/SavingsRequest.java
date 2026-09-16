package com.monikabiradar.personalfinancetracker.dto;

import java.math.BigDecimal;

public class SavingsRequest {

    private String savingsName;
    private BigDecimal targetAmount;

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
}
