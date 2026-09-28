package com.monikabiradar.personalfinancetracker.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class BudgetRequest {

    private BigDecimal totalBudget;
    private LocalDate month;

    public BigDecimal getTotalBudget() {
        return totalBudget;
    }

    public void setTotalBudget(BigDecimal totalBudget) {
        this.totalBudget = totalBudget;
    }

    public LocalDate getMonth() {
        return month;
    }

    public void setMonth(LocalDate month) {
        this.month = month;
    }
}
