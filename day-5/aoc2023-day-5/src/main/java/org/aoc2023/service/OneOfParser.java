package org.aoc2023.service;

import org.aoc2023.exception.ParseException;
import org.aoc2023.model.ParseResult;
import org.aoc2023.model.Parser;

import java.util.List;

public class OneOfParser<T> implements Parser<T> {
    private final List<Parser<T>> parsers;

    public OneOfParser(List<Parser<T>> parsers) {
        this.parsers = parsers;
    }

    @Override
    public ParseResult<T> parse(String input) throws ParseException {
        for (Parser<T> parser : parsers) {
            ParseResult<T> result = null;
            try {
                result = parser.parse(input);
            } catch (ParseException ex) {
                continue;
            }
            return result;
        }

        throw new ParseException("couldn't parse any for OneOfParser");
    }
}