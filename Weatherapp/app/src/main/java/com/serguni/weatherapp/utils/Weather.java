package com.serguni.weatherapp.utils;

import static com.serguni.weatherapp.utils.WeatherUtils.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class Weather {

    public enum WeatherType {
        CURRENT_WEATHER,
        FORECAST
    }

    private final static String API_CALL_FORECAST_TEMPLATE = "https://api.openweathermap.org/data/2.5/forecast?q=";
    private static final String API_CALL_CURRENT_WEATHER_TEMPLATE = "https://api.openweathermap.org/data/2.5/weather?q=";
    private static final String API_KEY_TEMPLATE = "&units=metric&appid=";


    public static String getWeatherInfo(String city, WeatherType weatherType) {
        switch (weatherType) {
            case FORECAST:
                return getForecastWeather(city);
            case CURRENT_WEATHER:
                return getCurrentWeather(city);
        }
        return "No place found.";
    }

    private static String getCurrentWeather(String city) {

        OpenWeatherRequest request = new OpenWeatherRequest(API_CALL_CURRENT_WEATHER_TEMPLATE,
                API_KEY_TEMPLATE,
                Key.OPEN_WEATHER_KEY);

        String json = request.getWeatherInfo(city);

        if ("404".equals(json))
            return "No place found";

        JsonElement jsonElement = JsonParser.parseString(json);
        JsonObject jo = jsonElement.getAsJsonObject();
        JsonObject weather = jo.get("weather").getAsJsonArray().get(0).getAsJsonObject();
        String wMain = weather.get("main").getAsString();
        JsonObject main = jo.get("main").getAsJsonObject();
        String visibility = jo.get("visibility").getAsString();
        JsonObject wind = jo.get("wind").getAsJsonObject();
        String country = jo.get("sys").getAsJsonObject().get("country").getAsString();
        long dt = jo.get("dt").getAsLong();
        long timeZone = jo.get("timezone").getAsLong();


        String utc = getUtc(timeZone); //to sb
        Date date = NormalizedDate.getNormalizedDate(dt, timeZone);

        SimpleDateFormat formatter = new SimpleDateFormat("EEEE, dd MMMM HH:mm yyyy", Locale.US);
        String fDate = formatter.format(date); // to sb

        int hours = date.getHours();

        return getFlag(country) + " " +
                '(' + country + ") " +
                city + '\n' +
                getIconTime(hours) + ' ' +
                fDate +
                " " + utc + '\n' +
                "Temperature: " + getFormatTemp(main.get("temp").getAsDouble()) + ' ' +
                WeatherUtils.weatherIconsCodes.get("Temperature") + '\n' +
                "Feels like: " + getFormatTemp(main.get("feels_like").getAsDouble()) + '\n' +
                wMain + ' ' + WeatherUtils.weatherIconsCodes.get(wMain) + '\n' +
                "Visibility: " + visibility + " m " +
                WeatherUtils.weatherIconsCodes.get("Visibility") + '\n' +
                "Wind: " + wind.get("speed") + " met/sec" + ' ' +
                WeatherUtils.weatherIconsCodes.get("Wind");
    }

    private static String getForecastWeather(String city) {
        OpenWeatherRequest request = new OpenWeatherRequest(API_CALL_FORECAST_TEMPLATE, API_KEY_TEMPLATE, Key.OPEN_WEATHER_KEY);

        String json = request.getWeatherInfo(city);

        if ("404".equals(json))
            return "404";

        JsonElement jsonElement = JsonParser.parseString(json);
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonArray list = jsonObject.get("list").getAsJsonArray();

        StringBuilder sb = new StringBuilder();

        long timezone = jsonObject.get("city").getAsJsonObject().get("timezone").getAsLong();
        String country = jsonObject.get("city").getAsJsonObject().get("country").getAsString();
        sb.append(getFlag(country)).append(" ")
                .append('(').append(country).append(") ")
                .append(city).append(' ')
                .append(getUtc(timezone)).append('\n');

        SimpleDateFormat formaterDay = new SimpleDateFormat("dd-MMMM, EEEE", Locale.US);
        SimpleDateFormat formaterTime = new SimpleDateFormat("HH:mm", Locale.US);

        int ndays = 0;
        long curDay = 0;
        int daysOfWeek = 7;

        for (JsonElement jse : list) {

            JsonObject jo = jse.getAsJsonObject();

            String t = getFormatTemp(jo.get("main").getAsJsonObject().get("temp").getAsDouble());
            String w = jo.get("weather").getAsJsonArray().get(0).getAsJsonObject().get("main").getAsString();
            long dt = jo.get("dt").getAsLong();

            Date date = NormalizedDate.getNormalizedDate(dt, timezone);

            int dN = date.getDate();

            if (curDay != dN) {
                curDay = dN;
                ndays++;
                if (ndays > daysOfWeek)
                    break;

                sb.append("\n\t").append(formaterDay.format(date)).append("\n");

            }

            sb.append(getIconTime(date.getHours())).append(' ')
                    .append(formaterTime.format(date)).append(' ')
                    .append(WeatherUtils.weatherIconsCodes.get("Temperature")).append(' ')
                    .append(t).append(' ')
                    .append(w).append(' ').append(WeatherUtils.weatherIconsCodes.get(w)).append("\n");
        }
        return sb.toString();
    }
}
