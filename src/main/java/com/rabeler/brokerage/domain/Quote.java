package com.rabeler.brokerage.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.Temporal;

public class Quote {
    private BigDecimal currentValue;
    private BigDecimal high, low, ask, bid, changePercent, changeTotal;
    private LocalDate date;
    private LocalTime time;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Quote{" +
                "currentValue=" + currentValue +
                ", high=" + high +
                ", low=" + low +
                ", ask=" + ask +
                ", bid=" + bid +
                ", changePercent=" + changePercent +
                ", changeTotal=" + changeTotal +
                ", date=" + date +
                ", time=" + time +
                '}';
    }
}
