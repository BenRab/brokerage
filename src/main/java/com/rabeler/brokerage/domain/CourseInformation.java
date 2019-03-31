package com.rabeler.brokerage.domain;

import java.math.BigDecimal;

public class CourseInformation {
    BigDecimal currentValue;

    public CourseInformation(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
    }
}
