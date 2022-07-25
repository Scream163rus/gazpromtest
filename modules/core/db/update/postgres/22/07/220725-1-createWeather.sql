create table GAZPROMTEST_WEATHER (
    ID uuid,
    VERSION integer not null,
    CREATE_TS timestamp,
    CREATED_BY varchar(50),
    UPDATE_TS timestamp,
    UPDATED_BY varchar(50),
    DELETE_TS timestamp,
    DELETED_BY varchar(50),
    --
    TEMP varchar(255),
    FEELS_LIKE varchar(255),
    PRESSURE varchar(255),
    HUMIDITY varchar(255),
    DESCRIPTION varchar(255),
    FROM_DATE timestamp,
    TO_DATE timestamp,
    WEATHER_DATA_ID uuid,
    --
    primary key (ID)
);