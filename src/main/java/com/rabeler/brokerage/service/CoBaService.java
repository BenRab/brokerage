package com.rabeler.brokerage.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class CoBaService {
    public String requestChart(String wkn, String market) {
        URL url = null;
        try {
            url = new URL("https://www.commerzbank.de/marktdaten/wp-services/advancedchart.php?TIMESPAN=1D&EXCHANGE=" + market +"&JSON=1&WKN=" + wkn +"&CURRENCY=EUR");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"))) {
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
