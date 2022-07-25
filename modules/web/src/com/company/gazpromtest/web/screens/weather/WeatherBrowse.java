package com.company.gazpromtest.web.screens.weather;

import com.company.gazpromtest.entity.City;
import com.company.gazpromtest.entity.Weather;
import com.company.gazpromtest.service.WeatherDataService;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.StandardLookup;
import com.haulmont.cuba.gui.screen.Subscribe;
import com.haulmont.cuba.gui.screen.Target;
import com.haulmont.cuba.gui.screen.UiController;
import com.haulmont.cuba.gui.screen.UiDescriptor;

import javax.inject.Inject;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@UiController("gazpromtest_Weather.browse")
@UiDescriptor("weather-browse.xml")
@LookupComponent("weathersTable")
@LoadDataBeforeShow
public class WeatherBrowse extends StandardLookup<Weather> {

    @Inject
    private WeatherDataService weatherDataService;
    @Inject
    private CollectionLoader<Weather> weathersDl;
    @Inject
    private LookupField<City> cityLookupField;
    @Inject
    private LookupField<LocalDateTime> fromDateField;
    @Inject
    private LookupField<LocalDateTime> toDateField;
    @Inject
    private CollectionContainer<Weather> weathersDc;
    @Inject
    private CheckBox currentWeatherCheckBox;

    @Install(to = "weathersDl", target = Target.DATA_LOADER)
    private List<Weather> weathersDlLoadDelegate(LoadContext<Weather> loadContext) {
        City city = cityLookupField.getValue();
        if(city == null){
            return Collections.emptyList();
        }
        LocalDateTime fromDateFieldValue = fromDateField.getValue();
        LocalDateTime toDate = toDateField.getValue();
        if(Boolean.FALSE.equals(currentWeatherCheckBox.getValue())) {
            List<Weather> weathers = weatherDataService.getWeatherDataByCityAndDate(city.getId(), fromDateFieldValue, toDate);
            weathers.sort(Comparator.comparing(Weather::getToDate));
            return weathers;
        }
        LocalDate localDateNow = LocalDate.now();
        LocalDateTime startOfDay =  LocalTime.MIN.atDate(localDateNow);
        LocalDateTime endDate = LocalTime.MAX.atDate(localDateNow);
        List<Weather> weathers = weatherDataService.getWeatherDataByCityAndDate(city.getId(), startOfDay, endDate);
        weathers.sort(Comparator.comparing(Weather::getToDate));
        return weathers;
    }

    @Subscribe("cityLookupField")
    public void onCityLookupFieldValueChange(HasValue.ValueChangeEvent<City> event) {
        if(event.getValue() != null) {
            fromDateField.setEditable(true);
            toDateField.setEditable(true);
            weathersDl.load();
            fromDateField.setOptionsList(weathersDc.getItems().stream().map(Weather::getFromDate).collect(Collectors.toList()));
            toDateField.setOptionsList(weathersDc.getItems().stream().map(Weather::getToDate).collect(Collectors.toList()));
            return;
        }
        weathersDl.load();
        fromDateField.setEditable(false);
        toDateField.setEditable(false);
    }

    @Subscribe("fromDateField")
    public void onFromDateFieldValueChange(HasValue.ValueChangeEvent event) {
        weathersDl.load();
    }

    @Subscribe("toDateField")
    public void onToDateFieldValueChange(HasValue.ValueChangeEvent event) {
        weathersDl.load();
    }

    @Subscribe("currentWeatherCheckBox")
    public void onCurrentWeatherCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        if(Boolean.TRUE.equals(event.getValue())) {
            fromDateField.setEditable(false);
            fromDateField.setValue(null);
            toDateField.setValue(null);
            toDateField.setEditable(false);
            weathersDl.load();
            return;
        }
        if(cityLookupField.getValue() != null) {
            fromDateField.setEditable(true);
            toDateField.setEditable(true);
            weathersDl.load();
        }
    }

}