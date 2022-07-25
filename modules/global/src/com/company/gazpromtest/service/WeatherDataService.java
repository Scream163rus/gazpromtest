package com.company.gazpromtest.service;

import com.company.gazpromtest.entity.Weather;
import com.company.gazpromtest.entity.WeatherData;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherDataService {
    String NAME = "gazpromtest_WeatherDataService";
    WeatherData getWeatherDataByCity(String city);
    List<Weather> getWeatherDataByCityAndDate(String city, LocalDateTime fromDate, LocalDateTime toDate);
    List<Weather> updateWeathers(WeatherData weatherData);
    boolean checkNeededUpdate(WeatherData weatherData);
}