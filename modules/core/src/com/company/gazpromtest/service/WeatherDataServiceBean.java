package com.company.gazpromtest.service;

import com.company.gazpromtest.config.OpenWeatherConfig;
import com.company.gazpromtest.dto.WeatherDataDto;
import com.company.gazpromtest.dto.WeatherDto;
import com.company.gazpromtest.entity.Weather;
import com.company.gazpromtest.entity.WeatherData;
import com.company.gazpromtest.parser.Parser;
import com.haulmont.cuba.core.global.Metadata;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service(WeatherDataService.NAME)
public class WeatherDataServiceBean implements WeatherDataService {

    @Inject
    private WeatherDataRepositoryService weatherDataRepositoryService;
    @Inject
    private OpenWeatherService openWeatherService;
    @Inject
    private Parser<WeatherDataDto> openWeatherMapXmlParser;
    @Inject
    private OpenWeatherConfig openWeatherConfig;
    @Inject
    private Metadata metadata;
    @Inject
    private WeatherRepositoryService weatherRepositoryService;

    public WeatherData getWeatherDataByWeatherDataCity(String city) {
        WeatherData weatherData = weatherDataRepositoryService.getWeatherDataByCity(city);
        if(weatherData == null){
            weatherData = metadata.create(WeatherData.class);
            weatherData.setCity(city);
        }
        if(checkNeededUpdate(weatherData)){
            List<Weather> weathers = updateWeathers(weatherData);
            if(weathers == null){
                return null;
            }
            WeatherData weatherDataSave = weatherDataRepositoryService.save(weatherData);
            weathers.forEach(weather -> weather.setWeatherData(weatherDataSave));
            List<Weather> saveWeathers = weatherRepositoryService.saveAll(weathers).stream()
                    .map(entity -> (Weather) entity)
                    .collect(Collectors.toList());
            weatherData.setWeatherList(saveWeathers);
        }
        return weatherData;
    }

    @Override
    public List<Weather> getWeatherDataByCity(String city) {
        WeatherData weatherDataByCity = weatherDataRepositoryService.getWeatherDataByCity(city);
        if(weatherDataByCity != null){
            return weatherDataByCity.getWeatherList();
        }
        return null;
    }

    @Override
    @Transactional
    public List<Weather> getWeatherDataByCityAndDate(String city, LocalDateTime fromDate, LocalDateTime toDate) {
        if(fromDate != null && toDate != null) {
            return weatherRepositoryService.getWeatherByCity(city, fromDate, toDate);
        }
        if(fromDate != null){
            return weatherRepositoryService.getWeatherByCity(city, fromDate);
        }
        if(toDate != null){
            return weatherRepositoryService.getWeatherByCity(toDate, city);
        }
        WeatherData weatherDataByWeatherDataCity = getWeatherDataByWeatherDataCity(city);
        if(weatherDataByWeatherDataCity == null){
            return null;
        }
        return weatherDataByWeatherDataCity.getWeatherList();
    }


    @Override
    @Transactional
    public List<Weather> updateWeathers(WeatherData weatherData) {
        String weathersXml = openWeatherService.getCityWeather(weatherData.getCity());
        if(weathersXml == null){
            return null;
        }
        List<WeatherDto> weatherDtoList = openWeatherMapXmlParser.parse(weathersXml, weatherData.getCity()).getWeatherList();
        List<Weather> newWeatherList = weatherDtoList.stream().map(WeatherDto::toWeather).collect(Collectors.toList());
        List<Weather> oldWeatherList = weatherData.getWeatherList();
        if(oldWeatherList == null || oldWeatherList.isEmpty()){
            return newWeatherList;
        }
        for (int i = 0; i < newWeatherList.size() ; i++) {
            Weather newWeather = newWeatherList.get(i);
            Weather oldWeather = oldWeatherList.get(i);
            oldWeather.setTemp(newWeather.getTemp());
            oldWeather.setDescription(newWeather.getDescription());
            oldWeather.setFeelsLike(newWeather.getFeelsLike());
            oldWeather.setFromDate(newWeather.getFromDate());
            oldWeather.setHumidity(newWeather.getHumidity());
            oldWeather.setPressure(newWeather.getPressure());
            oldWeather.setToDate(newWeather.getToDate());

        }
        return oldWeatherList;
    }

    @Override
    public boolean checkNeededUpdate(WeatherData weatherData) {
        if(weatherData != null){
            if(weatherData.getUpdateTs() == null){
                return true;
            }
            Date updateTs = weatherData.getUpdateTs();
            LocalDateTime dateTime = LocalDateTime.ofInstant(updateTs.toInstant(), ZoneId.systemDefault());
            return LocalDateTime.now().isAfter(dateTime.plusMinutes(openWeatherConfig.getUpdateTime()));
        }
        return true;
    }
}