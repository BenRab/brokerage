package com.rabeler.brokerage.service;

import com.rabeler.brokerage.domain.Quote;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CoBaService {
    public List<Quote> getQuotes(List<String> wkns, List<String> markets) {
        List<Quote> quoteResults = new ArrayList<>();
        StringBuilder marketBuilder = new StringBuilder();
        markets.forEach(market -> {marketBuilder.append(market); marketBuilder.append(",");});
        StringBuilder wknBuilder = new StringBuilder();
        markets.forEach(market -> {wknBuilder.append(market); wknBuilder.append(",");});

        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL("https://www.commerzbank.de/marktdaten/wp-services/pricedata.php?PROFIL=100&EXCHANGE=" +
                    marketBuilder.toString() +"&WKN=" + wknBuilder.toString());
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new InputSource(new StringReader(result.toString())));
            System.out.println(document.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return quoteResults;
    }

    public String requestChart(String wkn, String market) {
        try {
            URL url = new URL("https://www.commerzbank.de/marktdaten/wp-services/advancedchart.php?TIMESPAN=1D&EXCHANGE=" + market +"&JSON=1&WKN=" + wkn +"&CURRENCY=EUR");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    return line;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
