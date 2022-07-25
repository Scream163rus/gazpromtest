# Описание классов
### 1.OpenWeatherConfig - конфиг с основными параметрами
  ### Методы:
    getOpenWeatherForecastUrl - прямая ссылка на получение погоды по городу
    getUpdateTime - Частота обновления данных в минутах
### 2. WeatherData - сущность содержащая в себе название города и список сущностей Weather(погода)
### 3. Weather - сущность для хранения основной инфромации для погоды
### 4.OpenWeatherService - сервис работающий с апи OpenWeatherMap
  ### Метод:
    getCityWeather - получает в xml формате при помощи RestTemplate данные из OpenWeatherMap
### 5. WeatherDataRepositoryService - Репозиторий-сервис для работы с сущностью WeatherData
 ### Методы:
    save - сохранить сущность в бд
    getWeatherDataByCity - получить по названию города, сущность WeatherData
### 6. WeatherDataService - сервис с основной бизнес-логикой сущности WeatherData
 ### Методы:
    getWeatherDataByCity - метод, который обновляет данные сущности Weather и возвращает WeatherData c обновленными данными,
    если прошло достаточно времени, время берется из OpenWeatherConfig
    getWeatherDataByCityAndDate - на основе полученных параметров выбирает метод, который вытянит из бд нужные Weather
    updateWeathers - метод, который загружает новые данные с OpenWeatherMap и изменяет их в старых сущностях
    checkNeededUpdate - метод проверки нужно ли обновление
### 7. WeatherRepositoryService - Репозиторий - сервис, который работает с сущностю Weather
   ### Методы:
      saveAll - сохраняет список сущностей
      getWeatherByCity - перегруженные методы, которые на основе переданных полей достают из бд списки Weather
### 8. Parser - парсер xml данных из OpenWeatherMap
  ### Метод:
      parse - метод для парсинга
### 9. WeatherBrowse - Экран для отображения сущностей Weather
   ### Методы:
    weathersDlLoadDelegate - Метод из которого берет данные loader 
    onCityLookupFieldValueChange - хэндлер обрабатывающий изменение лукап филда с выбором города
    onFromDateFieldValueChange - хэндлер обрабатывающий изменение лукап филда "с даты"
    onToDateFieldValueChange - хэндлер обрабатывающий изменение лукап филда "по дату"
    onCurrentWeatherCheckBoxValueChange - хэндлер обрабатывающий изменение чекбокса
      
