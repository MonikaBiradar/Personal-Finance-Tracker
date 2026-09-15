package com.monikabiradar.personalfinancetracker.entity;

import com.monikabiradar.personalfinancetracker.enums.IncomeStatus;
import com.monikabiradar.personalfinancetracker.enums.UpdateFrequency;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "incomes")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incomeId;

    private String incomeName;

    private BigDecimal incomeAmount;

    @Enumerated(EnumType.STRING)
    private IncomeStatus incomeStatus;

    @Enumerated(EnumType.STRING)
    private UpdateFrequency updateFrequency;

    private LocalDate lastUpdatedDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

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

    public IncomeStatus getIncomeStatus() {
        return incomeStatus;
    }

    public void setIncomeStatus(IncomeStatus incomeStatus) {
        this.incomeStatus = incomeStatus;
    }

    public UpdateFrequency getUpdateFrequency() {
        return updateFrequency;
    }

    public void setUpdateFrequency(UpdateFrequency updateFrequency) {
        this.updateFrequency = updateFrequency;
    }

    public Long getIncomeId() {
        return incomeId;
    }

    public LocalDate getLastUpdatedDate() {
        return lastUpdatedDate;
    }
    public void setLastUpdatedDate(LocalDate lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
