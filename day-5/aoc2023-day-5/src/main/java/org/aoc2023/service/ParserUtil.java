package org.aoc2023.service;

import org.aoc2023.exception.ParseException;
import org.aoc2023.model.ParseResult;

import java.util.function.Function;

public class ParserUtil {
    public static ParseResult<String> extractWhile(String input, Function<Character, Boolean> predicate) {
        String out = "";

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (!predicate.apply(c)) {
                break;
            }
            out += c;
        }

        String rest = input.substring(out.length());
        return new ParseResult<>(out, rest);
    }

    public static ParseResult<String> extractWhile1(String input, Function<Character, Boolean> predicate) throws ParseException {
        ParseResult<String> result = extractWhile(input, predicate);
        if (result.value().isEmpty()) {
            throw new ParseException("expected at least one character to be parsed");
        }
        return result;
    }
}