package com.company.gazpromtest.dto;

import com.company.gazpromtest.entity.Weather;

import java.io.Serializable;
import java.time.LocalDateTime;

public class WeatherDto implements Serializable {
    private String temp;
    private String feelsLike;
    private String pressure;
    private String humidity;
    private String description;
    private String from;
    private String to;

    public WeatherDto(String temp, String feelsLike, String pressure, String humidity, String description, String from, String to) {
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.pressure = pressure;
        this.humidity = humidity;
        this.description = description;
        this.from = from;
        this.to = to;
    }

    public Weather toWeather(){
        Weather weather = new Weather();
        weather.setTemp(temp);
        weather.setFeelsLike(feelsLike);
        weather.setPressure(pressure);
        weather.setHumidity(humidity);
        weather.setDescription(description);
        weather.setFromDate(LocalDateTime.parse(from));
        weather.setToDate(LocalDateTime.parse(to));
        return weather;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(String feelsLike) {
        this.feelsLike = feelsLike;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
