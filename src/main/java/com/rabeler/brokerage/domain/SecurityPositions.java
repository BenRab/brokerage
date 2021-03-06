package com.rabeler.brokerage.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SecurityPositions {
    @Id
    ObjectId id;

    Security security;

    List<Position> positionList;

    PositionSummary positionSummary;

    public SecurityPositions() {};

    public SecurityPositions(ObjectId id, Security security) {
        this.security = security;
        this.positionList = new ArrayList<>();
    }

    public SecurityPositions(ObjectId id, Security security, List<Position> positionList, PositionSummary positionSummary) {
        this.id = id;
        this.security = security;
        this.positionList = positionList;
        this.positionSummary = positionSummary;
    }

    public Security getSecurity() {
        return security;
    }

    public List<Position> getPositionList() {
        return positionList;
    }

    public void addPosition(Position position) {
        this.positionList.add(position);
    }

    public PositionSummary getPositionSummary() {
        return positionSummary;
    }

    public void setPositionSummary(PositionSummary positionSummary) {
        this.positionSummary = positionSummary;
    }
}
