package com.rabeler.brokerage.service;

import com.rabeler.brokerage.domain.CourseInformation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
import java.util.function.BiFunction;
import java.util.function.Function;

@Service
public class Finanzen100ServiceImpl implements Finanzen100Service {

    @Override
    public CourseInformation getQuote(String id) {
        CourseInformation courseInformation = new CourseInformation();
        try {
            Document document = Jsoup.connect("https://www.finanzen100.de/aktien/" + id).get();
            courseInformation.setCurrentValue(getValue(document, ".quote__price__price"));
            courseInformation.setChangePercent(getValueForType(document, this::parsePositiveNegativeAndPercentToBigDecimal,
                    ".quote__price__pct"));
            courseInformation.setChangeTotal(getValueForType(document, this::parsePositiveNegativeToBigDecimal,
                    ".quote__price__abs"));
            courseInformation.setLow(getValueForType(document, this::parsePositiveNegativeToBigDecimal,
                    ".performance-overview__label .performance-overview__label__value"));
            courseInformation.setHigh(getValueForType(document, this::parsePositiveNegativeToBigDecimal,
                    ".performance-overview__label--right .performance-overview__label__value"));

            System.out.println(courseInformation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return courseInformation;
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

    private String getElements(Document document, String selectorClass) {
        Elements linksOnPage = getSelectedElements(document, selectorClass);
        return linksOnPage.get(0).getAllElements().get(0).html();
    }

    private Elements getSelectedElements(Document document, String selectorClass) {
        return document.select(selectorClass);
    }
}
