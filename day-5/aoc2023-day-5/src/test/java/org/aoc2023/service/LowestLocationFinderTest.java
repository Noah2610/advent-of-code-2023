package org.aoc2023.service;

import org.aoc2023.exception.FinderException;
import org.aoc2023.exception.ParseException;
import org.aoc2023.model.Almanac;
import org.aoc2023.model.Id;
import org.aoc2023.model.Parser;
import org.aoc2023.model.entity.Location;
import org.aoc2023.model.entity.Seed;
import org.aoc2023.service.parser.AlmanacParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LowestLocationFinderTest {
    @Test
    void findLowest() throws ParseException, FinderException {
        Lookup<Seed, Location> lookup = new SeedToLocationLookup();
        LowestLocationFinder finder = new LowestLocationFinder(lookup);
        Parser<Almanac> parser = new AlmanacParser();
        Almanac almanac = parser.parse("""
                seeds: 79 14 55 13

                seed-to-soil map:
                50 98 2
                52 50 48

                soil-to-fertilizer map:
                0 15 37
                37 52 2
                39 0 15

                fertilizer-to-water map:
                49 53 8
                0 11 42
                42 0 7
                57 7 4

                water-to-light map:
                88 18 7
                18 25 70

                light-to-temperature map:
                45 77 23
                81 45 19
                68 64 13

                temperature-to-humidity map:
                0 69 1
                1 0 69

                humidity-to-location map:
                60 56 37
                56 93 4
                """).value();

        assertEquals(new Location(Id.of(46)), finder.find(almanac));
    }
}