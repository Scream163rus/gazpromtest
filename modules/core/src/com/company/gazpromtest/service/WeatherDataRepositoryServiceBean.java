package com.company.gazpromtest.service;

import com.company.gazpromtest.entity.WeatherData;
import com.haulmont.cuba.core.TransactionalDataManager;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service(WeatherDataRepositoryService.NAME)
public class WeatherDataRepositoryServiceBean implements WeatherDataRepositoryService {

    @Inject
    private TransactionalDataManager transactionalDataManager;

    @Override
    public WeatherData getWeatherDataByCity(String city) {
        return transactionalDataManager.load(WeatherData.class)
                .query("select w from gazpromtest_WeatherData w where w.city =:city")
                .view("weatherData-view")
                .parameter("city", city)
                .optional()
                .orElse(null);
    }

    @Override
    public WeatherData save(WeatherData weatherData) {
        return transactionalDataManager.save(weatherData);
    }
}