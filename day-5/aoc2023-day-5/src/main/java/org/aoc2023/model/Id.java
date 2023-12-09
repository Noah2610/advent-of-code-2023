package org.aoc2023.model;

public record Id(int id) {
    public static Id of(int id) {
        return new Id(id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}