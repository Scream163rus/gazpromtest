package com.company.gazpromtest.service;

import com.company.gazpromtest.entity.Weather;
import com.haulmont.cuba.core.TransactionalDataManager;
import com.haulmont.cuba.core.global.EntitySet;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.util.List;

@Service(WeatherRepositoryService.NAME)
public class WeatherRepositoryServiceBean implements WeatherRepositoryService {

    @Inject
    private TransactionalDataManager transactionalDataManager;

    private final String VIEW = "weather-view";

    @Override
    public EntitySet saveAll(List<Weather> weathers) {
        return transactionalDataManager.save(weathers.toArray(new Weather[0]));
    }

    @Override
    public List<Weather> getWeatherByCity(String city, LocalDateTime fromDate) {
        return transactionalDataManager.load(Weather.class)
                .query("select w from gazpromtest_Weather w where w.weatherData.city =:city and w.fromDate >= :fromDate")
                .view(VIEW)
                .parameter("city", city)
                .parameter("fromDate", fromDate)
                .list();
    }

    @Override
    public List<Weather> getWeatherByCity(LocalDateTime toDate, String city) {
        return transactionalDataManager.load(Weather.class)
                .query("select w from gazpromtest_Weather w where w.weatherData.city =:city and w.toDate <= :toDate")
                .view(VIEW)
                .parameter("city", city)
                .parameter("toDate", toDate)
                .list();
    }

    @Override
    public List<Weather> getWeatherByCity(String city, LocalDateTime fromDate, LocalDateTime toDate) {
        return transactionalDataManager.load(Weather.class)
                .query("select w from gazpromtest_Weather w where w.weatherData.city =:city and w.fromDate >= :fromDate and w.toDate <= :toDate")
                .view(VIEW)
                .parameter("city", city)
                .parameter("fromDate", fromDate)
                .parameter("toDate", toDate)
                .list();

    }
}