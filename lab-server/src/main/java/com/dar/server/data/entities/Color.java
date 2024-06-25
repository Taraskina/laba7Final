package com.dar.server.data.entities;

public enum Color {
    RED,
    COMBI_PLASMA_GUN,
    BLUE,
    GREEN,
    YELLOW;

    public static Color choose(String s) {
        Color color = null;
        for (Color value : Color.values()) {
            if (value.name().equals(s)) {
                color = value;
            }
        }
        return color;
    }
}