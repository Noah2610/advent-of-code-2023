package org.aoc2023.service;

import org.aoc2023.exception.ParseException;
import org.aoc2023.model.ParseResult;
import org.aoc2023.model.Parser;

@FunctionalInterface
interface MapperFunction<T, R> {
    R apply(T t) throws ParseException;
}

public class MapperParser<R, T> implements Parser<R> {
    private final Parser<T> parser;
    private final MapperFunction<T, R> mapper;

    public MapperParser(Parser<T> parser, MapperFunction<T, R> mapper) {
        this.parser = parser;
        this.mapper = mapper;
    }

    @Override
    public ParseResult<R> parse(String input) throws ParseException {
        ParseResult<T> result = parser.parse(input);
        R mapped = mapper.apply(result.value());
        return new ParseResult<R>(mapped, result.rest());
    }
}