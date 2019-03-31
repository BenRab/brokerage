package com.rabeler.brokerage.domain;

import org.bson.types.ObjectId;

import java.math.BigDecimal;
import java.util.Date;

public class Position {
    ObjectId id;
    Date date;
    BigDecimal quantity;
    BigDecimal costs;
    BigDecimal buyInValue;
    BigDecimal exchangeRate;

    public Position(Date date, BigDecimal quantity, BigDecimal costs, BigDecimal buyInValue, BigDecimal exchangeRate) {
        this.id = new ObjectId();
        this.date = date;
        this.quantity = quantity;
        this.costs = costs;
        this.buyInValue = buyInValue;
        this.exchangeRate = exchangeRate;
    }

    public ObjectId getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public BigDecimal getCosts() {
        return costs;
    }

    public BigDecimal getBuyInValue() {
        return buyInValue;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }
}
