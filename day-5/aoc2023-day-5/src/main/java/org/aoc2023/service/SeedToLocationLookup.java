package org.aoc2023.service;

import org.aoc2023.exception.EntityMapCreationException;
import org.aoc2023.exception.EntityMapNotFoundException;
import org.aoc2023.exception.LookupException;
import org.aoc2023.model.Almanac;
import org.aoc2023.model.entity.Entity;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Location;
import org.aoc2023.model.entity.Seed;
import org.aoc2023.model.entityMap.EntityMap;

import java.util.List;

import static org.aoc2023.model.entity.EntityType.*;

public class SeedToLocationLookup {
    public Location lookup(Almanac almanac, Seed seed) throws LookupException {
        List<EntityType> entityTypeOrder = List.of(
                SEED,
                SOIL,
                FERTILIZER,
                WATER,
                LIGHT,
                TEMPERATURE,
                HUMIDITY,
                LOCATION
        );

        Entity sourceEntity = seed;

        for (int i = 0; i < entityTypeOrder.size(); i++) {
            EntityType sourceType = entityTypeOrder.get(i);
            EntityType destinationType = null;
            try {
                destinationType = entityTypeOrder.get(i + 1);
            } catch (IndexOutOfBoundsException ex) {
                break;
            }

            EntityMap<Entity, Entity> map = null;
            try {
                map = almanac.getMap(sourceType, destinationType);
            } catch (EntityMapNotFoundException ex) {
                throw new LookupException("Expected EntityMap", ex);
            }

            Entity destinationEntity = null;
            try {
                destinationEntity = map.getDestinationFor(sourceEntity);
            } catch (EntityMapCreationException ex) {
                throw new LookupException("Expected entity destination", ex);
            }

            sourceEntity = destinationEntity;
        }

        return (Location) sourceEntity;
    }
}