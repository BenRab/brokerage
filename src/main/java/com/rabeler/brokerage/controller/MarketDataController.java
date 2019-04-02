package com.rabeler.brokerage.controller;

import com.rabeler.brokerage.domain.AlphaVantageFunction;
import com.rabeler.brokerage.domain.CourseInformation;
import com.rabeler.brokerage.domain.QuotesAndStockInformations;
import com.rabeler.brokerage.domain.SecurityPositions;
import com.rabeler.brokerage.repository.BrokerageRepository;
import com.rabeler.brokerage.service.AlphaVintageService;
import com.rabeler.brokerage.service.Finanzen100Service;
import org.patriques.AlphaVantageConnector;
import org.patriques.BatchStockQuotes;
import org.patriques.input.Symbol;
import org.patriques.output.quote.BatchStockQuotesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.web.bind.annotation.*;
import pl.zankowski.iextrading4j.client.IEXTradingClient;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import java.util.Queue;



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

    private Queue<List<CourseInformation>> lastQuotes = new CircularFifoQueue<>(10);

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
        List<CourseInformation> courseInformations = new ArrayList<>(positions.size());
        for (SecurityPositions position : positions) {
            CourseInformation courseInformation = finanzen100Service.getQuote(position.getSecurity().getSecurityNumber());
            courseInformations.add(courseInformation);
        }
        this.lastQuotes.add(courseInformations);
        return new QuotesAndStockInformations(courseInformations, this.lastQuotes);
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
