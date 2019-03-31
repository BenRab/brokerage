package com.rabeler.brokerage.domain;

import org.patriques.input.ApiParameter;

public enum AlphaVantageFunction implements ApiParameter {
    GLOBAL_QUOTE("GLOBAL_QUOTE");

    private final String urlParameter;

    AlphaVantageFunction(String urlParameter) {
        this.urlParameter = urlParameter;
    }

    public String getKey() {
        return "function";
    }

    public String getValue() {
        return this.urlParameter;
    }
}
