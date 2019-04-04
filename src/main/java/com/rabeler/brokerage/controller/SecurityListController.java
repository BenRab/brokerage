package com.rabeler.brokerage.controller;

import com.rabeler.brokerage.domain.Position;
import com.rabeler.brokerage.domain.PositionSummary;
import com.rabeler.brokerage.domain.Security;
import com.rabeler.brokerage.domain.SecurityPositions;
import com.rabeler.brokerage.repository.BrokerageRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/account")
public class SecurityListController {
    @Autowired
    private BrokerageRepository brokerageRepository;

    @GetMapping("/list")
    @CrossOrigin(origins = "http://localhost:3000")
    public List<Object> list() {
        List<SecurityPositions> securityPositions = brokerageRepository.findAll();
        return List.of(securityPositions);
    }

    @GetMapping("/add")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object add() {
        Security security = new Security("ibm-wkn-851399_H1798111680_85502/?CODE_MARKET=_GAT", "IBM");
        Position position = new Position(Date.from(Instant.now()), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        SecurityPositions securityPositions = new SecurityPositions(new ObjectId(), security);
        PositionSummary positionSummary = new PositionSummary(new BigDecimal("124.69"), new BigDecimal("15"), BigDecimal.ONE, BigDecimal.ONE);

        securityPositions.addPosition(position);
        securityPositions.setPositionSummary(positionSummary);
        brokerageRepository.insert(securityPositions);

        return securityPositions;
    }
}
