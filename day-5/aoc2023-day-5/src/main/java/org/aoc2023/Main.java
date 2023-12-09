package org.aoc2023;

import org.aoc2023.exception.FinderException;
import org.aoc2023.exception.ParseException;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Day5 day5 = new Day5();

        try {
            day5.run();
        } catch (IOException | ParseException | FinderException ex) {
            throw new RuntimeException(ex);
        }
    }
}
