package com.rabeler.brokerage.service;

import com.rabeler.brokerage.domain.CourseInformation;
import com.rabeler.brokerage.domain.Quote;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class Finanzen100ServiceImpl implements Finanzen100Service {

    @Override
    public Quote getQuote(String id) {
        Quote quote = new Quote();
        try {
            Document document = Jsoup.connect("https://www.finanzen100.de/aktien/" + id).ignoreContentType(true).get();
            quote.setCurrentValue(getValue(document, ".quote__price__price"));
            quote.setChangePercent(getValueForType(document, this::parsePositiveNegativeAndPercentToBigDecimal,
                    ".quote__price__pct"));
            quote.setChangeTotal(getValueForType(document, this::parsePositiveNegativeToBigDecimal,
                    ".quote__price__abs"));
            quote.setLow(getValueForType(document, this::parsePositiveNegativeToBigDecimal,
                    ".performance-overview__label .performance-overview__label__value"));
            quote.setHigh(getValueForType(document, this::parsePositiveNegativeToBigDecimal,
                    ".performance-overview__label--right .performance-overview__label__value"));
            quote.setDate(getValueForType(document, this::parseQuoteDateField,
                    ".quote__price__date"));
            quote.setTime(getValueForType(document, this::parseQuoteTimeField,
                    ".quote__price__date"));

            System.out.println(quote);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return quote;
    }

    private <K> K getValueForType(Document document, Function<String, K> parsingFunction, String selectorClass) {
        String linksOnPage = getElements(document, selectorClass);
        return parsingFunction.apply(linksOnPage);
    }

    private BigDecimal getValue(Document document, String selectorClass) {
        return getValue(document, selectorClass, this::getElements);
    }

    private BigDecimal getValue(Document document, String selectorClass, BiFunction<Document, String, String> selectorFunction) {
        String selectedElement = selectorFunction.apply(document, selectorClass);
        return parseToBigDecimal(selectedElement);
    }

    private BigDecimal toNumber(String currentPrice) {
        NumberFormat nf = NumberFormat.getInstance(Locale.GERMAN);
        try {
            return new BigDecimal(nf.parse(currentPrice).toString());
        } catch (ParseException e) {
            e.printStackTrace();
            return BigDecimal.ZERO;
        }
    }

    private BigDecimal parseToBigDecimal(String currentPrice) {
        return toNumber(currentPrice);
    }

    private BigDecimal parsePositiveNegativeToBigDecimal(String currentPrice) {
        String removedPrice = StringUtils.deleteAny(currentPrice, "+-");
        BigDecimal number = toNumber(removedPrice);
        return currentPrice.startsWith("-") ? number.negate() : number;
    }

    private BigDecimal parsePositiveNegativeAndPercentToBigDecimal(String currentPrice) {
        String removedPrice = StringUtils.deleteAny(currentPrice, "+-%");
        BigDecimal number = toNumber(removedPrice);
        return currentPrice.startsWith("-") ? number.negate() : number;
    }

    private LocalDate parseQuoteDateField(String quoteDate) {
        String filteredQuoteDate = StringUtils.deleteAny(quoteDate, "| ");
        if (filteredQuoteDate.contains(".")) {
            //return LocalDate.parse(filteredQuoteDate, new DateTimeFormatter.("dd.MM.YYYY").parseDefaulting(ChronoField.YEAR, 2017));
        }
        return LocalDate.now();
    }

    private LocalTime parseQuoteTimeField(String quoteDate) {
        String filteredQuoteDate = StringUtils.deleteAny(quoteDate, "| ");
        if (filteredQuoteDate.contains(":")) {
            return LocalTime.parse(filteredQuoteDate, DateTimeFormatter.ofPattern("HH:mm:ss"));
        }
        return LocalTime.MIDNIGHT;
    }

    private String getElements(Document document, String selectorClass) {
        Elements linksOnPage = getSelectedElements(document, selectorClass);
        if (linksOnPage.get(0).getAllElements().isEmpty()) {
            System.out.println(document);
            return "";
        }
        return linksOnPage.get(0).getAllElements().get(0).html();
    }

    private Elements getSelectedElements(Document document, String selectorClass) {
        return document.select(selectorClass);
    }
}
