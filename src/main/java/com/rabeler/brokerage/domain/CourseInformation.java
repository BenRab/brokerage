package com.rabeler.brokerage.domain;

import java.math.BigDecimal;

public class CourseInformation {
    private BigDecimal currentValue;
    private BigDecimal high, low, ask, bid, changePercent, changeTotal;

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public void setHigh(BigDecimal high) {
        this.high = high;
    }

    public BigDecimal getLow() {
        return low;
    }

    public void setLow(BigDecimal low) {
        this.low = low;
    }

    public BigDecimal getAsk() {
        return ask;
    }

    public void setAsk(BigDecimal ask) {
        this.ask = ask;
    }

    public BigDecimal getBid() {
        return bid;
    }

    public void setBid(BigDecimal bid) {
        this.bid = bid;
    }

    public BigDecimal getChangePercent() {
        return changePercent;
    }

    public void setChangePercent(BigDecimal changePercent) {
        this.changePercent = changePercent;
    }

    public BigDecimal getChangeTotal() {
        return changeTotal;
    }

    public void setChangeTotal(BigDecimal changeTotal) {
        this.changeTotal = changeTotal;
    }

    @Override
    public String toString() {
        return "CourseInformation{" +
                "currentValue=" + currentValue +
                ", high=" + high +
                ", low=" + low +
                ", ask=" + ask +
                ", bid=" + bid +
                ", changePercent=" + changePercent +
                ", changeTotal=" + changeTotal +
                '}';
    }
}
