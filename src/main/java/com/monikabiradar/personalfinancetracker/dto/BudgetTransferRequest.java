package com.monikabiradar.personalfinancetracker.dto;

import java.math.BigDecimal;

public class BudgetTransferRequest {

    private Long sourceAllocation;
    private Long destinationAllocation;
    private BigDecimal amount;


    public Long getSourceAllocation() {
        return sourceAllocation;
    }

    public void setSourceAllocation(Long sourceAllocation) {
        this.sourceAllocation = sourceAllocation;
    }

    public Long getDestinationAllocation() {
        return destinationAllocation;
    }

    public void setDestinationAllocation(Long destinationAllocation) {
        this.destinationAllocation = destinationAllocation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
