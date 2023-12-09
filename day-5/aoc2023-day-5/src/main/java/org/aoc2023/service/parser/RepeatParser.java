package org.aoc2023.service.parser;

import org.aoc2023.exception.ParseException;
import org.aoc2023.model.ParseResult;
import org.aoc2023.model.Parser;

import java.util.LinkedList;
import java.util.List;

public class RepeatParser<T> implements Parser<List<T>> {
    private final Parser<T> parser;

    public RepeatParser(Parser<T> parser) {
        this.parser = parser;
    }

    @Override
    public ParseResult<List<T>> parse(String input) throws ParseException {
        List<T> parsed = new LinkedList<>();

        String rest = input;

        while (true) {
            ParseResult<T> result = null;
            try {
                result = parser.parse(rest);
            } catch (ParseException ex) {
                break;
            }

            rest = result.rest();
            parsed.add(result.value());
        }

        if (parsed.isEmpty()) {
            throw new ParseException("RepeatParser expected at least one parse iteration");
        }

        return new ParseResult<>(parsed, rest);
    }
}