package com.rabeler.brokerage.service;

import com.rabeler.brokerage.domain.Quote;

public interface Finanzen100Service {
    Quote getQuote(String id);
}
