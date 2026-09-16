package com.monikabiradar.personalfinancetracker.dto;

import java.math.BigDecimal;

public class CurrentSavingsAmountRequest {

    private BigDecimal newCurrentAmount;

    public BigDecimal getNewCurrentAmount() {
        return newCurrentAmount;
    }

    public void setNewCurrentAmount(BigDecimal newCurrentAmount) {
        this.newCurrentAmount = newCurrentAmount;
    }
}
