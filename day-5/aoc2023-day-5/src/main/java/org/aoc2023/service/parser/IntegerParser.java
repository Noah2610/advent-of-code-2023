package org.aoc2023.service.parser;

import org.aoc2023.exception.ParseException;
import org.aoc2023.model.ParseResult;
import org.aoc2023.model.Parser;

public class IntegerParser implements Parser<Long> {
    @Override
    public ParseResult<Long> parse(String input) throws ParseException {
        ParseResult<String> result = ParserUtil.extractWhile1(input, c -> Character.isDigit(c));
        String digitsS = result.value();
        String rest = result.rest();

        Long num = null;
        try {
            num = Long.parseLong(digitsS);
        } catch (NumberFormatException ex) {
            throw new ParseException(String.format("couldn't parse string \"%s\" to integer for IntegerParser", digitsS));
        }

        return new ParseResult<>(num, rest);
    }
}