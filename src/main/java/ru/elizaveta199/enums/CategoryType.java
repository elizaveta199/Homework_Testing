package ru.elizaveta199.enums;

import lombok.Getter;

public enum CategoryType {
    FOOD(1, "Food"),
    FURNITURE(567, "Furniture");

    @Getter
    private int id;
    @Getter
    private String name;

    CategoryType(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
