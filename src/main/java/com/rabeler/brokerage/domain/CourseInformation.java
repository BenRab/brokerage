package com.rabeler.brokerage.domain;

import java.util.Collection;

public class CourseInformation {
    private Quote currentQuote;
    private Collection<Quote> lastQuotes;

    public Collection<Quote> getLastQuotes() {
        return lastQuotes;
    }

    public void setLastQuotes(Collection<Quote> lastQuotes) {
        this.lastQuotes = lastQuotes;
    }

    public Quote getCurrentQuote() {
        return currentQuote;
    }

    public void setCurrentQuote(Quote currentQuote) {
        this.currentQuote = currentQuote;
    }
}
