package com.rabeler.brokerage.service;

import java.math.BigDecimal;

public class Finanzen100Response {
    BigDecimal currentPrice;
    String Currency;
    BigDecimal changeAbsolute;
    BigDecimal changeRelative;
    BigDecimal min;
    BigDecimal max;
    BigDecimal before;
}
