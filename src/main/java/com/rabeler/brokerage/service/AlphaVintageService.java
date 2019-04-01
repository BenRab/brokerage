package com.rabeler.brokerage.service;

import com.rabeler.brokerage.domain.AlphaVantageFunction;
import org.patriques.AlphaVantageConnector;
import org.patriques.BatchStockQuotes;
import org.patriques.input.Symbol;
import org.patriques.output.quote.BatchStockQuotesResponse;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AlphaVintageService {

    @Async
    public String getQuote(String securityNumber) {
        String apiKey = "GR4ATDG8BD62EV52";
        int timeout = 3000;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
        BatchStockQuotes batchStockQuotes = new BatchStockQuotes(apiConnector);
        BatchStockQuotesResponse batchStockQuotesResponse = batchStockQuotes.quote(securityNumber);
        return apiConnector.getRequest(new Symbol(securityNumber), AlphaVantageFunction.GLOBAL_QUOTE);
    }
}
