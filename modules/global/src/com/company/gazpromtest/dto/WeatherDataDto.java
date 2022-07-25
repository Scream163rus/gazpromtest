package com.company.gazpromtest.dto;

import com.company.gazpromtest.entity.Weather;
import com.company.gazpromtest.entity.WeatherData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WeatherDataDto implements Serializable {
    private String city;
    private List<WeatherDto> weatherList;

    public WeatherDataDto(String city, List<WeatherDto> weatherList) {
        this.city = city;
        this.weatherList = weatherList;
    }

    public WeatherData toWeatherData(){
        List<Weather> weathers = new ArrayList<>();
        WeatherData weatherData = new WeatherData();
        weatherData.setCity(city);
        weatherList.forEach(weatherDto -> {
            weathers.add(weatherDto.toWeather());
        });
        weatherData.setWeatherList(weathers);
        return weatherData;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<WeatherDto> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<WeatherDto> weatherList) {
        this.weatherList = weatherList;
    }
}