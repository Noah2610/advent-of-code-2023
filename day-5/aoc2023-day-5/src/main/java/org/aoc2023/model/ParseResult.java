package org.aoc2023.model;

public record ParseResult<T>(T value, String rest) {
}