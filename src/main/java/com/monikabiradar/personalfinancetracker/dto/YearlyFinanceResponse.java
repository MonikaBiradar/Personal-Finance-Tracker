package com.monikabiradar.personalfinancetracker.dto;

import java.math.BigDecimal;

public class YearlyFinanceResponse {

    private BigDecimal yearlyIncome;
    private BigDecimal yearlyExpenses;
    private BigDecimal yearlySavings;

    public YearlyFinanceResponse(BigDecimal yearlyIncome, BigDecimal yearlyExpenses, BigDecimal yearlySavings) {
        this.yearlyIncome = yearlyIncome;
        this.yearlyExpenses = yearlyExpenses;
        this.yearlySavings = yearlySavings;
    }

    public BigDecimal getYearlyIncome() {
        return yearlyIncome;
    }

    public BigDecimal getYearlyExpenses() {
        return yearlyExpenses;
    }

    public BigDecimal getYearlySavings() {
        return yearlySavings;
    }
}
