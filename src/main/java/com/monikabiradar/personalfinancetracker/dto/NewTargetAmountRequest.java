package com.monikabiradar.personalfinancetracker.dto;

import java.math.BigDecimal;

public class NewTargetAmountRequest {

    private BigDecimal newTargetAmount;

    public BigDecimal getNewTargetAmount() {
        return newTargetAmount;
    }

    public void setNewTargetAmount(BigDecimal newTargetAmount) {
        this.newTargetAmount = newTargetAmount;
    }
}
