package com.company.gazpromtest.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.defaults.DefaultLong;
import com.haulmont.cuba.core.config.defaults.DefaultString;

public interface OpenWeatherConfig extends Config {

    @Property("api.openweather.forecast")
    @DefaultString("https://api.openweathermap.org/data/2.5/forecast?mode=xml&units=metric&lang=ru&appid=8663bf28ca4b91c02e1a7314e9cdfccd&q=%s")
    String getOpenWeatherForecastUrl();

    @Property("api.updatetime")
    @DefaultLong(30)
    long getUpdateTime();

    @Property("api.openweather.current")
    @DefaultString("https://api.openweathermap.org/data/2.5/weather?mode=xml&units=metric&lang=ru&appid=8663bf28ca4b91c02e1a7314e9cdfccd&q=%s")
    String getOpenWeatherCurrentUrl();

}
