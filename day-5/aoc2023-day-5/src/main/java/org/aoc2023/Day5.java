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
import java.util.logging.Level;
import java.util.logging.Logger;

public class Day5 {
    private static final String INPUT_FILE = "./input.txt";
    private final Logger logger;

    public Day5() {
        this.logger = Logger.getLogger(Day5.class.getSimpleName());
        this.logger.setLevel(Level.INFO);
//        this.logger.addHandler(new ConsoleHandler());
    }


    public void run() throws IOException, ParseException, FinderException {
        logger.config("Creating AlmanacParser");
        Parser<Almanac> parser = new AlmanacParser();
        logger.config("Creating SeedToLocationLookup");
        Lookup<Seed, Location> lookup = new SeedToLocationLookup();
        logger.config("Creating LowestLocationFinder");
        Finder<Location> finder = new LowestLocationFinder(lookup);

        logger.info("Reading input from file");
        String input = readInput();

        logger.info("Parsing almanac from input");
        Almanac almanac = parser.parse(input).value();
        logger.info("Finding lowest location in almanac");
        Entity entity = finder.find(almanac);

        logger.info("Done");

        System.out.println(entity);
    }

    private static String readInput() throws IOException {
        Charset encoding = StandardCharsets.UTF_8;
        Path path = Path.of(INPUT_FILE);
        return Files.readString(path, encoding);
    }
}