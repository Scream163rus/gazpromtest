package com.company.gazpromtest.web.screens.weather;

import com.company.gazpromtest.entity.City;
import com.company.gazpromtest.entity.Weather;
import com.company.gazpromtest.service.WeatherDataService;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.gui.Dialogs;
import com.haulmont.cuba.gui.Notifications;
import com.haulmont.cuba.gui.components.Action;
import com.haulmont.cuba.gui.components.CheckBox;
import com.haulmont.cuba.gui.components.DialogAction;
import com.haulmont.cuba.gui.components.HasValue;
import com.haulmont.cuba.gui.components.LookupField;
import com.haulmont.cuba.gui.model.CollectionContainer;
import com.haulmont.cuba.gui.model.CollectionLoader;
import com.haulmont.cuba.gui.screen.Install;
import com.haulmont.cuba.gui.screen.LoadDataBeforeShow;
import com.haulmont.cuba.gui.screen.LookupComponent;
import com.haulmont.cuba.gui.screen.MessageBundle;
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
    @Inject
    private Dialogs dialogs;
    private List<Weather> weathers;
    @Inject
    private Notifications notifications;
    @Inject
    private MessageBundle messageBundle;

    @Install(to = "weathersDl", target = Target.DATA_LOADER)
    private List<Weather> weathersDlLoadDelegate(LoadContext<Weather> loadContext) {
        City city = cityLookupField.getValue();
        if(city == null){
            return Collections.emptyList();
        }
        if(weathers == null){
            return Collections.emptyList();
        }
        weathers.sort(Comparator.comparing(Weather::getToDate));
        return weathers;
    }

    private void getWeathers(City city) {
        LocalDateTime fromDateFieldValue = fromDateField.getValue();
        LocalDateTime toDate = toDateField.getValue();
        if(Boolean.FALSE.equals(currentWeatherCheckBox.getValue())) {
            weathers = weatherDataService.getWeatherDataByCityAndDate(city.getId(), fromDateFieldValue, toDate);
            return;
        }
        LocalDate localDateNow = LocalDate.now();
        LocalDateTime startOfDay =  LocalTime.MIN.atDate(localDateNow);
        LocalDateTime endDate = LocalTime.MAX.atDate(localDateNow);
        weathers = weatherDataService.getWeatherDataByCityAndDate(city.getId(), startOfDay, endDate);
    }

    @Subscribe("cityLookupField")
    public void onCityLookupFieldValueChange(HasValue.ValueChangeEvent<City> event) {
        City value = event.getValue();
        weathers = null;
        if(value != null) {
            getWeathers(value);
            if(weathers == null || weathers.isEmpty()){
                dialogs.createOptionDialog()
                        .withCaption(messageBundle.getMessage("serviceNotResponding"))
                        .withMessage(messageBundle.getMessage("serviceOpenWeatherNotResponding"))
                        .withActions(
                                new DialogAction(DialogAction.Type.YES, Action.Status.PRIMARY).withHandler(e -> {
                                    weathers = weatherDataService.getWeatherDataByCity(value.getId());
                                    if(weathers == null){
                                        createNotification("error", "dataNotFound", Notifications.NotificationType.ERROR);
                                        cityLookupField.setValue(null);
                                        return;
                                    }
                                    fromDateField.setEditable(true);
                                    toDateField.setEditable(true);
                                    weathersDl.load();
                                    fromDateField.setOptionsList(weathersDc.getItems().stream().map(Weather::getFromDate).collect(Collectors.toList()));
                                    toDateField.setOptionsList(weathersDc.getItems().stream().map(Weather::getToDate).collect(Collectors.toList()));
                                }),
                                new DialogAction(DialogAction.Type.NO).withHandler(e ->{
                                    cityLookupField.setValue(null);
                                })
                        )
                        .show();
                return;
            }
            fromDateField.setEditable(true);
            toDateField.setEditable(true);
            currentWeatherCheckBox.setEditable(true);
            weathersDl.load();
            fromDateField.setOptionsList(weathersDc.getItems().stream().map(Weather::getFromDate).collect(Collectors.toList()));
            toDateField.setOptionsList(weathersDc.getItems().stream().map(Weather::getToDate).collect(Collectors.toList()));
            return;
        }
        weathersDl.load();
        fromDateField.setEditable(false);
        fromDateField.setValue(null);
        toDateField.setValue(null);
        toDateField.setEditable(false);
        currentWeatherCheckBox.setEditable(false);
        currentWeatherCheckBox.setValue(false);
    }

    @Subscribe("fromDateField")
    public void onFromDateFieldValueChange(HasValue.ValueChangeEvent event) {
        LocalDateTime toDate = toDateField.getValue();
        LocalDateTime fromDate = fromDateField.getValue();
        if(fromDate != null && toDate != null && fromDate.isAfter(toDate)){
            createNotification("formatDate", "dateMayBeOnlyPositive", Notifications.NotificationType.TRAY);
            return;
        }
        City value = cityLookupField.getValue();
        getWeathers(value);
        weathersDl.load();
    }

    private void createNotification(String caption, String description, Notifications.NotificationType notificationType) {
        notifications.create(notificationType)
                .withCaption(messageBundle.getMessage(caption))
                .withDescription(messageBundle.getMessage(description))
                .show();
    }

    @Subscribe("toDateField")
    public void onToDateFieldValueChange(HasValue.ValueChangeEvent event) {
        LocalDateTime toDate = toDateField.getValue();
        LocalDateTime fromDate = fromDateField.getValue();
        if(fromDate != null && toDate != null && toDate.isBefore(fromDate)){
            createNotification("formatDate", "dateMayBeOnlyPositive", Notifications.NotificationType.TRAY);
            return;
        }
        City value = cityLookupField.getValue();
        getWeathers(value);
        weathersDl.load();
    }

    @Subscribe("currentWeatherCheckBox")
    public void onCurrentWeatherCheckBoxValueChange(HasValue.ValueChangeEvent<Boolean> event) {
        List<Weather> items = weathersDc.getItems();
        City value = cityLookupField.getValue();
        if(items.isEmpty()){
            return;
        }
        if(Boolean.TRUE.equals(event.getValue())) {
            fromDateField.setEditable(false);
            fromDateField.setValue(null);
            toDateField.setValue(null);
            toDateField.setEditable(false);
            getWeathers(value);
            weathersDl.load();
            return;
        }
        if(cityLookupField.getValue() != null) {
            fromDateField.setEditable(true);
            toDateField.setEditable(true);
            getWeathers(value);
            weathersDl.load();
        }
    }

}