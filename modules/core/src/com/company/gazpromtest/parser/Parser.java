package com.company.gazpromtest.parser;

public interface Parser<T> {
    T parse(String xml, String cityName);
}
