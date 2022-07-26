package com.company.gazpromtest.service;

import com.company.gazpromtest.config.OpenWeatherConfig;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
        try {
            ResponseEntity<String> forEntity = restTemplate.getForEntity(String.format(openWeatherConfig.getOpenWeatherForecastUrl(), city), String.class);
            if(forEntity.getStatusCode() == HttpStatus.OK) {
                return forEntity.getBody();
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }
}