package com.example.truthordare.utils;

import java.io.Serializable;

public class StaticEnum implements Serializable {
    private static final long serialVersionUID = 1;
    private String description;
    private String name;
    private String value;

    public enum KEY_TYPE {
        NAME,
        VALUE
    }

    public StaticEnum(String name, String value, String description) {
        this.name = name;
        this.value = value;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public String getValue() {
        return this.value;
    }

    public String getDescription() {
        return this.description;
    }

    public String toString() {
        return getDescription();
    }

    public static final <T extends StaticEnum> T forName(T[] data, String name) {
        for (T sEnum : data) {
            if (sEnum.getName().equals(name)) {
                return sEnum;
            }
        }
        return null;
    }
}
