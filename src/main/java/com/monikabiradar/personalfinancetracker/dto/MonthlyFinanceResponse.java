package com.monikabiradar.personalfinancetracker.dto;

import java.math.BigDecimal;

public class MonthlyFinanceResponse {

    private final BigDecimal monthlyIncome;
    private final BigDecimal monthlyExpenses;
    private final BigDecimal monthlySavings;

    public MonthlyFinanceResponse(BigDecimal monthlyIncome, BigDecimal monthlyExpenses, BigDecimal monthlySavings) {
        this.monthlyIncome = monthlyIncome;
        this.monthlyExpenses = monthlyExpenses;
        this.monthlySavings = monthlySavings;
    }

    public BigDecimal getMonthlyIncome() {
        return monthlyIncome;
    }

    public BigDecimal getMonthlySavings() {
        return monthlySavings;
    }

    public BigDecimal getMonthlyExpenses() {
        return monthlyExpenses;
    }
}
