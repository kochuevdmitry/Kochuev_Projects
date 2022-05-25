package com.example.TestCirclesAndRectangles.data.enums;

public enum ColorEnum {
    RED,
    GREEN,
    BLUE,
    TRANSPARENT;

    public static ColorEnum getColorEnumByString(String color) {
        switch (color) {
            case "red":
                return RED;
            case "green":
                return GREEN;
            case "blue":
                return BLUE;
            default:
                return TRANSPARENT;
        }
    }
}
