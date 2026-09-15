package com.monikabiradar.personalfinancetracker.dto;

import com.monikabiradar.personalfinancetracker.enums.UpdateFrequency;

import java.math.BigDecimal;

public class IncomeRequest {
    private String incomeName;
    private BigDecimal incomeAmount;
    private UpdateFrequency updateFrequency;


    public String getIncomeName() {
        return incomeName;
    }

    public void setIncomeName(String incomeName) {
        this.incomeName = incomeName;
    }

    public BigDecimal getIncomeAmount() {
        return incomeAmount;
    }

    public void setIncomeAmount(BigDecimal incomeAmount) {
        this.incomeAmount = incomeAmount;
    }

    public UpdateFrequency getUpdateFrequency() {
        return updateFrequency;
    }

    public void setUpdateFrequency(UpdateFrequency updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

}
