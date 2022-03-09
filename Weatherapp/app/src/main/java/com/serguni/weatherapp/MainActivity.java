package com.serguni.weatherapp;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.serguni.weatherapp.utils.Weather;

public class MainActivity extends CustomActivity {

    TextView textView;
    EditText editText;
    Button getCurrentWeather;
    Button getForecastWeather;

    private void setWeatherListener(View button, Weather.WeatherType weatherType) {
        button.setOnClickListener(view -> {
            String text = editText.getText().toString();
            String weather = "";

            if (!text.equals("")) {
                weather = Weather.getWeatherInfo(text, weatherType);
            }

            textView.setText(weather);
        });
    }

    protected void init() {
        textView = findViewById(R.id.textView);
        editText = findViewById(R.id.editText);
        getForecastWeather = findViewById(R.id.getForecastWeather);
        getCurrentWeather = findViewById(R.id.getCurrentWeather);

        setWeatherListener(getForecastWeather, Weather.WeatherType.FORECAST);
        setWeatherListener(getCurrentWeather, Weather.WeatherType.CURRENT_WEATHER);
    }
}