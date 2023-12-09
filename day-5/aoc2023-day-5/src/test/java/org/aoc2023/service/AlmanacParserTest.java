package org.aoc2023.service;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.exception.EntityMapNotFoundException;
import org.aoc2023.exception.ParseException;
import org.aoc2023.model.Almanac;
import org.aoc2023.model.EntityRange;
import org.aoc2023.model.Id;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Seed;
import org.aoc2023.model.entityMap.*;
import org.aoc2023.service.parser.AlmanacParser;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlmanacParserTest {

    @Test
    void parse() throws ParseException, EntityMapNotFoundException, EntityInstantiationException {
        AlmanacParser parser = new AlmanacParser();

        Almanac almanac = parser.parse("""
                seeds: 0 1

                seed-to-soil map:
                0 1 1

                soil-to-fertilizer map:
                0 1 1

                fertilizer-to-water map:
                0 1 1

                water-to-light map:
                0 1 1

                light-to-temperature map:
                0 1 1

                temperature-to-humidity map:
                0 1 1

                humidity-to-location map:
                0 1 1
                """).value();

        assertEquals(List.of(new EntityRange<Seed>(new Seed(Id.of(0)), 1, Seed.class)), almanac.getSeedsRanges());
        assertEquals(new SeedToSoil(List.of(new EntityMapConfig(1, 0, 1))).toString(), almanac.getMap(EntityType.SEED, EntityType.SOIL).toString());
        assertEquals(new SoilToFertilizer(List.of(new EntityMapConfig(1, 0, 1))).toString(), almanac.getMap(EntityType.SOIL, EntityType.FERTILIZER).toString());
        assertEquals(new FertilizerToWater(List.of(new EntityMapConfig(1, 0, 1))).toString(), almanac.getMap(EntityType.FERTILIZER, EntityType.WATER).toString());
        assertEquals(new WaterToLight(List.of(new EntityMapConfig(1, 0, 1))).toString(), almanac.getMap(EntityType.WATER, EntityType.LIGHT).toString());
        assertEquals(new LightToTemperature(List.of(new EntityMapConfig(1, 0, 1))).toString(), almanac.getMap(EntityType.LIGHT, EntityType.TEMPERATURE).toString());
        assertEquals(new TemperatureToHumidity(List.of(new EntityMapConfig(1, 0, 1))).toString(), almanac.getMap(EntityType.TEMPERATURE, EntityType.HUMIDITY).toString());
        assertEquals(new HumidityToLocation(List.of(new EntityMapConfig(1, 0, 1))).toString(), almanac.getMap(EntityType.HUMIDITY, EntityType.LOCATION).toString());
    }
}