package com.rabeler.brokerage.domain;

public class PositionRequest {
    private Security security;
    private Position position;

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "PositionRequest{" +
                "security=" + security +
                ", position=" + position +
                '}';
    }
}
