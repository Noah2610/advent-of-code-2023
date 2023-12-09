package org.aoc2023.model;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.exception.EntityMapNotFoundException;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Seed;
import org.aoc2023.model.entity.Soil;
import org.aoc2023.model.entityMap.EntityMap;
import org.aoc2023.model.entityMap.EntityMapConfig;
import org.aoc2023.model.entityMap.SeedToSoil;
import org.aoc2023.model.entityMap.SoilToFertilizer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AlmanacTest {
    @Test
    void getMap() throws EntityInstantiationException, EntityMapNotFoundException {
        List<Range<Seed>> seedRanges = List.of(new EntityRange<Seed>(new Seed(Id.of(0)), 1, Seed.class));
        List<EntityMap<?, ?>> maps = List.of(
                new SeedToSoil(List.of(new EntityMapConfig(0, 10, 1))),
                new SoilToFertilizer(List.of(new EntityMapConfig(10, 20, 1)))
        );

        Almanac almanac = new Almanac(seedRanges, maps);

        assertEquals(
                new Soil(Id.of(10)),
                almanac.getMap(EntityType.SEED, EntityType.SOIL)
                        .getDestinationFor(new Seed(Id.of(0)))
        );
    }
}