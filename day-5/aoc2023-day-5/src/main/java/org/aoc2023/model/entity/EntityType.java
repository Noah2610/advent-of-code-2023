package org.aoc2023.model.entity;

public enum EntityType {
    SEED,
    SOIL,
    FERTILIZER,
    WATER,
    LIGHT,
    TEMPERATURE,
    HUMIDITY,
    LOCATION;


    @Override
    public String toString() {
        return switch (this) {
            case SEED -> "Seed";
            case SOIL -> "Soil";
            case FERTILIZER -> "Fertilizer";
            case WATER -> "Water";
            case LIGHT -> "Light";
            case TEMPERATURE -> "Temperature";
            case HUMIDITY -> "Humidity";
            case LOCATION -> "Location";
        };
    }
}