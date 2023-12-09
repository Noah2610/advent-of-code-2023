package org.aoc2023.service.parser;

import org.aoc2023.exception.ParseException;
import org.aoc2023.model.ParseResult;
import org.aoc2023.model.Parser;

public class TokenParser implements Parser<String> {
    private final String token;

    public TokenParser(String token) {
        this.token = token;
    }

    @Override
    public ParseResult<String> parse(String input) throws ParseException {
        if (!input.startsWith(token)) {
            throw new ParseException(String.format("token \"%s\"", token));
        }
        return new ParseResult<>(token, input.substring(token.length()));
    }
}