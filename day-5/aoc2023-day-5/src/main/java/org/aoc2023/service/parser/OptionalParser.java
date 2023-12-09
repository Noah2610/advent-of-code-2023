package org.aoc2023.service.parser;

import org.aoc2023.exception.ParseException;
import org.aoc2023.model.ParseResult;
import org.aoc2023.model.Parser;

import java.util.Optional;

public class OptionalParser<T> implements Parser<Optional<T>> {
    private final Parser<T> parser;

    public OptionalParser(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    public ParseResult<Optional<T>> parse(String input) throws ParseException {
        ParseResult<T> result = null;
        try {
            result = parser.parse(input);
        } catch (ParseException ex) {
            return new ParseResult<>(Optional.empty(), input);
        }
        return new ParseResult<>(Optional.of(result.value()), result.rest());
    }
}