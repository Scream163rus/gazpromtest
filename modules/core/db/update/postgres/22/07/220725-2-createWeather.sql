alter table GAZPROMTEST_WEATHER add constraint FK_GAZPROMTEST_WEATHER_ON_WEATHER_DATA foreign key (WEATHER_DATA_ID) references GAZPROMTEST_WEATHER_DATA(ID);
create index IDX_GAZPROMTEST_WEATHER_ON_WEATHER_DATA on GAZPROMTEST_WEATHER (WEATHER_DATA_ID);
