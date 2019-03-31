package com.rabeler.brokerage.controller;

import com.rabeler.brokerage.domain.CourseInformation;
import com.rabeler.brokerage.domain.Security;
import com.rabeler.brokerage.domain.SecurityPositions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/marketdata")
public class MarketDataController {

    @GetMapping("/list")
    public Object collectMarketInformation(Security security) {
        return new CourseInformation(security, BigDecimal.ONE);
    }
}
