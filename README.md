# Описание классов<br/>
### 1.OpenWeatherConfig - конфиг с основными параметрами<br/>
  ### Методы:<br/>
    getOpenWeatherForecastUrl - прямая ссылка на получение погоды по городу<br/>
    getUpdateTime - Частота обновления данных в минутах<br/>
### 2. WeatherData - сущность содержащая в себе название города и список сущностей Weather(погода)<br/>
### 3. Weather - сущность для хранения основной инфромации для погоды<br/>
### 4.OpenWeatherService - сервис работающий с апи OpenWeatherMap<br/>
  ### Метод:<br/>
    getCityWeather - получает в xml формате при помощи RestTemplate данные из OpenWeatherMap<br/>
### 5. WeatherDataRepositoryService - Репозиторий-сервис для работы с сущностью WeatherData<br/>
 ### Методы:<br/>
    save - сохранить сущность в бд<br/>
    getWeatherDataByCity - получить по названию города, сущность WeatherData<br/>
### 6. WeatherDataService - сервис с основной бизнес-логикой сущности WeatherData<br/>
 ### Методы:<br/>
    getWeatherDataByCity - метод, который обновляет данные сущности Weather и возвращает WeatherData c обновленными данными, <br/>
    если прошло достаточно времени, время берется из OpenWeatherConfig<br/>
    getWeatherDataByCityAndDate - на основе полученных параметров выбирает метод, который вытянит из бд нужные Weather<br/>
    updateWeathers - метод, который загружает новые данные с OpenWeatherMap и изменяет их в старых сущностях<br/>
    checkNeededUpdate - метод проверки нужно ли обновление<br/>
### 7. WeatherRepositoryService - Репозиторий - сервис, который работает с сущностю Weather<br/>
   ### Методы:<br/>
      saveAll - сохраняет список сущностей<br/>
      getWeatherByCity - перегруженные методы, которые на основе переданных полей достают из бд списки Weather<br/>
### 8. Parser - парсер xml данных из OpenWeatherMap<br/>
  ### Метод:<br/>
      parse - метод для парсинга<br/>
### 9. WeatherBrowse - Экран для отображения сущностей Weather
   ### Методы: <br/>
    weathersDlLoadDelegate - Метод из которого берет данные loader <br/>
    onCityLookupFieldValueChange - хэндлер обрабатывающий изменение лукап филда с выбором города<br/>
    onFromDateFieldValueChange - хэндлер обрабатывающий изменение лукап филда "с даты"<br/>
    onToDateFieldValueChange - хэндлер обрабатывающий изменение лукап филда "по дату"<br/>
    onCurrentWeatherCheckBoxValueChange - хэндлер обрабатывающий изменение чекбокса<br/>
      
