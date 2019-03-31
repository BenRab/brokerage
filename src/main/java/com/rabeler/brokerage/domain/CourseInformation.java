package com.rabeler.brokerage.domain;

import java.math.BigDecimal;

public class CourseInformation {
    private BigDecimal currentValue;
    private Security security;

    public CourseInformation(Security security, BigDecimal currentValue) {
        this.security = security;
        this.currentValue = currentValue;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }
}
