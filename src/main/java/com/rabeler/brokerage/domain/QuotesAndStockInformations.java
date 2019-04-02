package com.rabeler.brokerage.domain;

import java.util.List;
import java.util.Queue;

public class QuotesAndStockInformations {
    private List<CourseInformation> courseInformations;
    private Queue<List<CourseInformation>> lastQuotes;

    public QuotesAndStockInformations(List<CourseInformation> courseInformations, Queue<List<CourseInformation>> lastQuotes) {
        this.courseInformations = courseInformations;
        this.lastQuotes = lastQuotes;
    }

    public List<CourseInformation> getCourseInformations() {
        return courseInformations;
    }

    public Queue<List<CourseInformation>> getLastQuotes() {
        return lastQuotes;
    }
}
