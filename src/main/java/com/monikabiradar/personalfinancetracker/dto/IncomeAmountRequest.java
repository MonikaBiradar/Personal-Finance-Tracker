package com.monikabiradar.personalfinancetracker.dto;

import java.math.BigDecimal;

public class IncomeAmountRequest {
    private BigDecimal newIncomeAmount;

    public BigDecimal getNewIncomeAmount() {
        return newIncomeAmount;
    }
    public void setNewIncomeAmount(BigDecimal newIncomeAmount) {
        this.newIncomeAmount = newIncomeAmount;
    }
}
