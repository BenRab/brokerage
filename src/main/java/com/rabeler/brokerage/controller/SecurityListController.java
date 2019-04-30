package com.rabeler.brokerage.controller;

import com.rabeler.brokerage.domain.*;
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
        var securityPositions = brokerageRepository.findAll();
        return List.of(securityPositions);
    }

    @GetMapping("/add")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object add() {
        var security = new Security("daimler-wkn-710000_H208355456_82840/?CODE_MARKET=_GAT", "Daimler");
        security.setWkn("710000");
        security.setMarket("GAT");
        var position = new Position(Date.from(Instant.now()), BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ONE);
        var securityPositions = new SecurityPositions(new ObjectId(), security);
        var positionSummary = new PositionSummary(new BigDecimal("61.22"), new BigDecimal("15"), new BigDecimal("4.5"), BigDecimal.ONE);

        securityPositions.addPosition(position);
        securityPositions.setPositionSummary(positionSummary);
        brokerageRepository.insert(securityPositions);

        return securityPositions;
    }

    @PostMapping("/addPosition")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object addPosition(@RequestBody PositionRequest request) {
        var security = request.getSecurity();
        var securityPositions = new SecurityPositions(new ObjectId(), security);

        securityPositions.addPosition(request.getPosition());
        // brokerageRepository.insert(securityPositions);

        return securityPositions;
    }

    @PostMapping("/addNewPosition")
    @CrossOrigin(origins = "http://localhost:3000")
    public Object addNewPosition(@RequestBody PositionRequest request) {
        var security = request.getSecurity();
        var securityPositions = new SecurityPositions(new ObjectId(), security);

        securityPositions.addPosition(request.getPosition());
        // brokerageRepository.insert(securityPositions);

        return securityPositions;
    }
}
