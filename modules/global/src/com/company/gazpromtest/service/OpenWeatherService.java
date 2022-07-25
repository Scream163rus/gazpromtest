package com.company.gazpromtest.service;

public interface OpenWeatherService {
    String NAME = "gazpromtest_OpenWeatherService";
    String getCityWeather(String city);
}