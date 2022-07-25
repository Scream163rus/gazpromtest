package com.company.gazpromtest.service;

import com.company.gazpromtest.config.OpenWeatherConfig;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.inject.Inject;

@Service(OpenWeatherService.NAME)
public class OpenWeatherServiceBean implements OpenWeatherService {

    @Inject
    private RestTemplate restTemplate;
    @Inject
    private OpenWeatherConfig openWeatherConfig;

    @Override
    public String getCityWeather(String city) {
        return restTemplate.getForEntity(String.format(openWeatherConfig.getOpenWeatherForecastUrl(), city), String.class).getBody();
    }
}