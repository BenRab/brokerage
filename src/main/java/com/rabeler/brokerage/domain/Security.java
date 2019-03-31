package com.rabeler.brokerage.domain;

import org.bson.types.ObjectId;

public class Security {
    private String securityNumber;
    private String fullQualifiedName;
    private ObjectId id;

    public Security(String securityNumber, String fullQualifiedName) {
        this.securityNumber = securityNumber;
        this.fullQualifiedName = fullQualifiedName;
        this.id = new ObjectId();
    }

    public String getSecurityNumber() {
        return securityNumber;
    }

    public void setSecurityNumber(String securityNumber) {
        this.securityNumber = securityNumber;
    }

    public String getFullQualifiedName() {
        return fullQualifiedName;
    }

    public void setFullQualifiedName(String fullQualifiedName) {
        this.fullQualifiedName = fullQualifiedName;
    }
}
