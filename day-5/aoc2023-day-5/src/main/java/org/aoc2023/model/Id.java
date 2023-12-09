package org.aoc2023.model;

public record Id(long id) {
    public static Id of(long id) {
        return new Id(id);
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}