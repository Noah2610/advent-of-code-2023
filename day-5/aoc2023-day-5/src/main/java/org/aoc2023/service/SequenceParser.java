package org.aoc2023.service;

import org.aoc2023.exception.ParseException;
import org.aoc2023.model.ParseResult;
import org.aoc2023.model.Parser;

import java.util.LinkedList;
import java.util.List;

public class SequenceParser<T> implements Parser<List<T>> {
    private final List<Parser<?>> parsers;

    public SequenceParser(List<Parser<?>> parsers) {
        this.parsers = parsers;
    }

    @Override
    public ParseResult<List<T>> parse(String input) throws ParseException {
        String rest = input;
        List<Object> results = new LinkedList<>();

        for (var parser : parsers) {
            ParseResult<?> result = parser.parse(rest);
            rest = result.rest();
            results.add(result.value());
        }

        return new ParseResult<>((List<T>) results, rest);
    }
}