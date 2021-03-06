package com.serguni.weatherapp.utils;

import android.annotation.SuppressLint;

import java.util.HashMap;
import java.util.Map;

public class WeatherUtils {
        public final static Map<String, String> weatherIconsCodes = new HashMap<>();

        static {
            weatherIconsCodes.put("Clear", "ā");
            weatherIconsCodes.put("Rain", "ā");
            weatherIconsCodes.put("Snow", "ā");
            weatherIconsCodes.put("Clouds", "ā");
            weatherIconsCodes.put("Mist", "šļø");
            weatherIconsCodes.put("Morning", "š");
            weatherIconsCodes.put("Noon", "š");
            weatherIconsCodes.put("Evening", "š");
            weatherIconsCodes.put("Night", "š");
            weatherIconsCodes.put("Temperature", "š”");
            weatherIconsCodes.put("Visibility", "š");
            weatherIconsCodes.put("Wind", "šØ");

        }


    @SuppressLint("DefaultLocale")
    public static String getFormatTemp(double temp) {
        return String.format("%.0f CĀ°", temp);
    }

    public static String getUtc(long timezone) {

        StringBuilder result = new StringBuilder();
        result.append("UTC");

        int utc = (int) (timezone / 3600);
        int utcP = (int) (timezone / 60) % 60;

        if (utc > 0) {
            result.append("+").append(utc);
        } else {
            result.append(utc);
        }

        if (utcP != 0) {
            result.append(":").append(utcP);
        }

        return result.toString();
    }

    public static String getIconTime(int hours) {
        String iconTime;
        if (5 <= hours && hours < 10) {
            iconTime = "Morning";
        } else if (10 <= hours && hours < 18) {
            iconTime = "Noon";
        } else if (18 <= hours && hours < 21) {
            iconTime = "Evening";
        } else {
            iconTime = "Night";
        }

        return WeatherUtils.weatherIconsCodes.get(iconTime);
    }

    public static String getFlag(String country) {
        int flagOffset = 0x1F1E6;
        int asciiOffset = 0x41;

        int firstChar = Character.codePointAt(country, 0) - asciiOffset + flagOffset;
        int secondChar = Character.codePointAt(country, 1) - asciiOffset + flagOffset;

        return new String(Character.toChars(firstChar))
                + new String(Character.toChars(secondChar));

    }
}
