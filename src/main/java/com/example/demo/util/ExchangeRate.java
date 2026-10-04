package com.example.demo.util;

import org.springframework.web.client.RestClient;
import java.util.Map;

public class ExchangeRate {
    private static final String API_KEY = "dbf566ed5f485a64493f4360";
    private static final String BASE_URL = "https://v6.exchangerate-api.com";

    public double getExchangeRate(String currency) {

        RestClient restClient = RestClient.builder()
                .baseUrl(BASE_URL)
                .build();


        Map<String, Object> response = restClient.get()
                .uri("/v6/" + API_KEY + "/latest/" + currency)
                .retrieve()
                .body(Map.class);


        if (response != null && response.containsKey("conversion_rates")) {

            Map<String, Object> rates = (Map<String, Object>) response.get("conversion_rates");
            Object nokRate = rates.get("NOK");

            if (nokRate instanceof Integer) {
                 return ((Integer) nokRate).doubleValue();
            } else if (nokRate instanceof Double) {
                return ((Double) nokRate);

            }
            else {
                throw new RuntimeException("Bad response from server");
            }
        }

        throw new RuntimeException("Could not reach API.");
    }

    public double convertCurrencyToNok(double amount, String currency) {
        return amount * getExchangeRate(currency);
    }

}

