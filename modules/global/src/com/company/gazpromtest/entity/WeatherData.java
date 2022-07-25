package com.company.gazpromtest.entity;

import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Table(name = "GAZPROMTEST_WEATHER_DATA")
@Entity(name = "gazpromtest_WeatherData")
public class WeatherData extends StandardEntity {
    private static final long serialVersionUID = 7075759997419612278L;

    @Column(name = "CITY")
    private String city;

    @OneToMany(mappedBy = "weatherData")
    private List<Weather> weatherList;

    public List<Weather> getWeatherList() {
        return weatherList;
    }

    public void setWeatherList(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}