package com.rabeler.brokerage.controller;

import com.rabeler.brokerage.domain.*;
import com.rabeler.brokerage.repository.BrokerageRepository;
import com.rabeler.brokerage.service.AlphaVintageService;
import com.rabeler.brokerage.service.CoBaService;
import com.rabeler.brokerage.service.Finanzen100Service;
import org.patriques.AlphaVantageConnector;
import org.patriques.BatchStockQuotes;
import org.patriques.input.Symbol;
import org.patriques.output.quote.BatchStockQuotesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.zankowski.iextrading4j.client.IEXTradingClient;

import java.util.*;

import org.apache.commons.collections4.queue.CircularFifoQueue;


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

    @Autowired
    private CoBaService coBaService;

    private Map<String, CircularFifoQueue<Quote>> lastQuotes = new HashMap<>();

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

    @GetMapping("/chart/{securityNumber}/{market}")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object collectChartData(@PathVariable String securityNumber, @PathVariable String market) {
        return coBaService.requestChart(securityNumber, market);
    }

    @GetMapping("/quotes")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object collectQuotes() {
        List<SecurityPositions> positions = brokerageRepository.findAll();
        if (lastQuotes.isEmpty()) {
            positions.forEach(position -> lastQuotes.put(
                    position.getSecurity().getSecurityNumber(), new CircularFifoQueue<>(10)));
        }
        List<CourseInformation> courseInformations = new ArrayList<>(positions.size());
        for (SecurityPositions position : positions) {
            Quote quote = finanzen100Service.getQuote(position.getSecurity().getSecurityNumber());
            CircularFifoQueue<Quote> lastQuotes = this.lastQuotes.get(position.getSecurity().getSecurityNumber());
            if (lastQuotes.peek() == null ||
                    quote.getCurrentValue() != null && !quote.getCurrentValue().equals(
                            lastQuotes.get(lastQuotes.size() - 1).getCurrentValue()))
                lastQuotes.add(quote);

            CourseInformation courseInformation = new CourseInformation();
            courseInformation.setLastQuotes(lastQuotes);
            courseInformation.setCurrentQuote(quote);

            courseInformations.add(courseInformation);
        }
        return courseInformations;
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
