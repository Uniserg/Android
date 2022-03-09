package com.serguni.weatherapp.utils;

public class OpenWeatherRequest {

    protected final String apiCallTemplate;
    protected final String apiKeyTemplate;
    private final String apiKey;

    public OpenWeatherRequest(String apiCallTemplate, String apiKeyTemplate, String apiKey) {
        this.apiCallTemplate = apiCallTemplate;
        this.apiKeyTemplate = apiKeyTemplate;
        this.apiKey = apiKey;
    }

    public String getWeatherInfo(String city) {
        final String[] result = {""};
        String url = apiCallTemplate + city + apiKeyTemplate + apiKey;


        Thread thread = new Thread(() -> result[0] = JsonReader.readJsonFromUrl(url));

        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result[0];
    }
}
