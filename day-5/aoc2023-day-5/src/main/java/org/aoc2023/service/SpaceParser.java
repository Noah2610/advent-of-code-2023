package org.aoc2023.service;

import org.aoc2023.exception.ParseException;
import org.aoc2023.model.ParseResult;
import org.aoc2023.model.Parser;

public class SpaceParser implements Parser<String> {

    @Override
    public ParseResult<String> parse(String input) throws ParseException {
        return ParserUtil.extractWhile1(input, c -> Character.isSpaceChar(c));
    }
}