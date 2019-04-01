package com.rabeler.brokerage.service;

import com.rabeler.brokerage.domain.CourseInformation;

public interface Finanzen100Service {
    CourseInformation getQuote(String id);
}
