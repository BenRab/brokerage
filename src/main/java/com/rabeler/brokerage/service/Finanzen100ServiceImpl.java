package com.rabeler.brokerage.service;

import com.rabeler.brokerage.domain.CourseInformation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;
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

            System.out.println(courseInformation);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private <K> K getValueForType(Document document, Function<Elements, K> parsingFunction, String selectorClass) {
        Elements linksOnPage = getElements(document, selectorClass);
        return parsingFunction.apply(linksOnPage);
    }

    private BigDecimal getValue(Document document, String selectorClass) {
        Elements linksOnPage = getElements(document, selectorClass);
        return parseToBigDecimal(linksOnPage);
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

    private BigDecimal parseToBigDecimal(Elements linksOnPage) {
        String currentPrice = linksOnPage.get(0).getAllElements().get(0).html();
        return toNumber(currentPrice);
    }

    private BigDecimal parsePositiveNegativeToBigDecimal(Elements linksOnPage) {
        String currentPrice = linksOnPage.get(0).getAllElements().get(0).html();
        String removedPrice = StringUtils.deleteAny(currentPrice, "+-");
        BigDecimal number = toNumber(removedPrice);
        return currentPrice.startsWith("-") ? number.negate() : number;
    }

    private BigDecimal parsePositiveNegativeAndPercentToBigDecimal(Elements linksOnPage) {
        String currentPrice = linksOnPage.get(0).getAllElements().get(0).html();
        String removedPrice = StringUtils.deleteAny(currentPrice, "+-%");
        BigDecimal number = toNumber(removedPrice);
        return currentPrice.startsWith("-") ? number.negate() : number;
    }

    private Elements getElements(Document document, String selectorClass) {
        return document.select(selectorClass);
    }
}
