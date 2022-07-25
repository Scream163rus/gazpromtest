package com.company.gazpromtest.entity;

import com.haulmont.chile.core.datatypes.impl.EnumClass;

import javax.annotation.Nullable;


public enum City implements EnumClass<String> {

    TOGLIATTI("Togliatti"),
    SAMARA("Samara");

    private String id;

    City(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static City fromId(String id) {
        for (City at : City.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}