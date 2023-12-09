package org.aoc2023.model;

import org.aoc2023.exception.ParseException;

public interface Parser<T> {
    ParseResult<T> parse(String input) throws ParseException;
}