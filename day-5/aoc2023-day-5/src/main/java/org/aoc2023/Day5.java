package org.aoc2023;

import org.aoc2023.exception.FinderException;
import org.aoc2023.exception.ParseException;
import org.aoc2023.model.Almanac;
import org.aoc2023.model.Parser;
import org.aoc2023.model.entity.Entity;
import org.aoc2023.model.entity.Location;
import org.aoc2023.model.entity.Seed;
import org.aoc2023.service.Finder;
import org.aoc2023.service.Lookup;
import org.aoc2023.service.LowestLocationFinder;
import org.aoc2023.service.SeedToLocationLookup;
import org.aoc2023.service.parser.AlmanacParser;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class Day5 {
    private static String INPUT_FILE = "./input.txt";

    public void run() throws IOException, ParseException, FinderException {
        Parser<Almanac> parser = new AlmanacParser();
        Lookup<Seed, Location> lookup = new SeedToLocationLookup();
        Finder<Location> finder = new LowestLocationFinder(lookup);

        String input = readInput();

        Almanac almanac = parser.parse(input).value();
        Entity entity = finder.find(almanac);

        System.out.println(entity);
    }

    private static String readInput() throws IOException {
        Charset encoding = StandardCharsets.UTF_8;
        Path path = Path.of(INPUT_FILE);
        return Files.readString(path, encoding);
    }
}