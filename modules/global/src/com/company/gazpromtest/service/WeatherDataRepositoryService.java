package com.company.gazpromtest.service;

import com.company.gazpromtest.entity.WeatherData;

public interface WeatherDataRepositoryService {
    String NAME = "gazpromtest_WeatherDataRepositoryService";
    WeatherData save(WeatherData weatherData);
    WeatherData getWeatherDataByCity(String city);
}