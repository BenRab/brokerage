package com.rabeler.brokerage.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

public class PositionSummary {
    @Id
    ObjectId id;

    BigDecimal amountInvested;

    BigDecimal amount;

    BigDecimal amountCosts;

    BigDecimal dividendTotal;

    public PositionSummary(BigDecimal amountInvested, BigDecimal amount, BigDecimal amountCosts, BigDecimal dividendTotal) {
        this.id = new ObjectId();
        this.amountInvested = amountInvested;
        this.amount = amount;
        this.amountCosts = amountCosts;
        this.dividendTotal = dividendTotal;
    }

    public BigDecimal getAmountInvested() {
        return amountInvested;
    }

    public void setAmountInvested(BigDecimal amountInvested) {
        this.amountInvested = amountInvested;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getAmountCosts() {
        return amountCosts;
    }

    public void setAmountCosts(BigDecimal amountCosts) {
        this.amountCosts = amountCosts;
    }

    public BigDecimal getDividendTotal() {
        return dividendTotal;
    }

    public void setDividendTotal(BigDecimal dividendTotal) {
        this.dividendTotal = dividendTotal;
    }
}
