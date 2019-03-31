package com.rabeler.brokerage.controller;

import com.rabeler.brokerage.domain.AlphaVantageFunction;
import com.rabeler.brokerage.domain.CourseInformation;
import com.rabeler.brokerage.domain.Security;
import org.patriques.BatchStockQuotes;
import org.patriques.input.ApiParameter;
import org.patriques.input.Function;
import org.patriques.input.Symbol;
import org.patriques.input.Symbols;
import org.patriques.output.quote.BatchStockQuotesResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.zankowski.iextrading4j.api.marketdata.TOPS;
import pl.zankowski.iextrading4j.api.refdata.ExchangeSymbol;
import pl.zankowski.iextrading4j.api.stocks.*;
import pl.zankowski.iextrading4j.client.IEXTradingClient;
import pl.zankowski.iextrading4j.client.rest.request.refdata.SymbolsRequestBuilder;
import pl.zankowski.iextrading4j.client.rest.request.stocks.*;
import pl.zankowski.iextrading4j.client.socket.manager.SocketRequest;
import pl.zankowski.iextrading4j.client.socket.request.marketdata.TopsAsyncRequestBuilder;

import org.patriques.AlphaVantageConnector;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("/marketdata")
public class MarketDataController {
    private final IEXTradingClient iexTradingClient = IEXTradingClient.create();

    @GetMapping("/quoteAccurate/{securityNumber}")
    public Object collectMarketInformation(@PathVariable String securityNumber) {
        String apiKey = "GR4ATDG8BD62EV52";
        int timeout = 3000;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
        BatchStockQuotes batchStockQuotes = new BatchStockQuotes(apiConnector);
        BatchStockQuotesResponse batchStockQuotesResponse = batchStockQuotes.quote(securityNumber);
        return apiConnector.getRequest(new Symbol(securityNumber), AlphaVantageFunction.GLOBAL_QUOTE);
    }

    @GetMapping("/quote/{securityNumber}")
    public Object collectMarketInformationFinanzen100(@PathVariable String securityNumber) {
        String apiKey = "GR4ATDG8BD62EV52";
        int timeout = 3000;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
        BatchStockQuotes batchStockQuotes = new BatchStockQuotes(apiConnector);
        BatchStockQuotesResponse batchStockQuotesResponse = batchStockQuotes.quote(securityNumber);
        return apiConnector.getRequest(new Symbol(securityNumber), AlphaVantageFunction.GLOBAL_QUOTE);
    }
}
