package com.rabeler.brokerage.controller;

import com.rabeler.brokerage.domain.AlphaVantageFunction;
import com.rabeler.brokerage.domain.CourseInformation;
import com.rabeler.brokerage.domain.Security;
import com.rabeler.brokerage.domain.SecurityPositions;
import com.rabeler.brokerage.repository.BrokerageRepository;
import com.rabeler.brokerage.service.AlphaVintageService;
import com.rabeler.brokerage.service.Finanzen100Service;
import org.patriques.BatchStockQuotes;
import org.patriques.input.ApiParameter;
import org.patriques.input.Function;
import org.patriques.input.Symbol;
import org.patriques.input.Symbols;
import org.patriques.output.quote.BatchStockQuotesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/marketdata")
public class MarketDataController {
    private final IEXTradingClient iexTradingClient = IEXTradingClient.create();

    @Autowired
    private AlphaVintageService alphaVintageService;

    @Autowired
    private Finanzen100Service finanzen100Service;

    @Autowired
    private BrokerageRepository brokerageRepository;

    @GetMapping("/quoteAccurate/{securityNumber}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object collectMarketInformation(@PathVariable String securityNumber) {
        return alphaVintageService.getQuote(securityNumber);
    }

    @GetMapping("/quote/{securityNumber}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object collectMarketDataFinanzen100(@PathVariable String securityNumber) {
        return finanzen100Service.getQuote(securityNumber);
    }

    @GetMapping("/quotes")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object collectQuotes() {
        List<SecurityPositions> positions = brokerageRepository.findAll();
        List<Pair<SecurityPositions, CourseInformation>> quotes = new ArrayList<>(positions.size());
        for (SecurityPositions position : positions) {
            CourseInformation courseInformation = finanzen100Service.getQuote(position.getSecurity().getSecurityNumber());
            quotes.add(Pair.of(position, courseInformation));
        }
        return quotes;
    }

    @GetMapping("/quoteInac/{securityNumber}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object collectMarketInformationFinanzen100(@PathVariable String securityNumber) {
        String apiKey = "GR4ATDG8BD62EV52";
        int timeout = 3000;
        AlphaVantageConnector apiConnector = new AlphaVantageConnector(apiKey, timeout);
        BatchStockQuotes batchStockQuotes = new BatchStockQuotes(apiConnector);
        BatchStockQuotesResponse batchStockQuotesResponse = batchStockQuotes.quote(securityNumber);
        return apiConnector.getRequest(new Symbol(securityNumber), AlphaVantageFunction.GLOBAL_QUOTE);
    }
}
