package com.company.gazpromtest.service;

import com.company.gazpromtest.entity.Weather;
import com.haulmont.cuba.core.global.EntitySet;

import java.time.LocalDateTime;
import java.util.List;

public interface WeatherRepositoryService {
    String NAME = "gazpromtest_WeatherRepositoryService";
    EntitySet saveAll(List<Weather> weathers);
    List<Weather> getWeatherByCity(String city, LocalDateTime fromDate);
    List<Weather> getWeatherByCity(LocalDateTime toDate, String city);
    List<Weather> getWeatherByCity(String city, LocalDateTime fromDate, LocalDateTime toDate);
}