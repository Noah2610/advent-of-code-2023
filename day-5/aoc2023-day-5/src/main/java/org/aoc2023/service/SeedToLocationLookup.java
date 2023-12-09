package org.aoc2023.service;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.exception.EntityMapNotFoundException;
import org.aoc2023.exception.LookupException;
import org.aoc2023.model.Almanac;
import org.aoc2023.model.entity.Entity;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Location;
import org.aoc2023.model.entity.Seed;
import org.aoc2023.model.entityMap.EntityMap;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.aoc2023.model.entity.EntityType.*;

public class SeedToLocationLookup implements Lookup<Seed, Location> {
    private final Logger logger;

    public SeedToLocationLookup() {
        this.logger = Logger.getLogger(SeedToLocationLookup.class.getSimpleName());
        this.logger.setLevel(Level.OFF);
    }

    @Override
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

        logger.info("Iterating through entityTypeOrder");
        for (int i = 0; i < entityTypeOrder.size(); i++) {
            EntityType sourceType = entityTypeOrder.get(i);
            EntityType destinationType = null;
            try {
                destinationType = entityTypeOrder.get(i + 1);
            } catch (IndexOutOfBoundsException ex) {
                break;
            }

            logger.info(String.format("Attempting lookup for source/destination: %s, %s", sourceType, destinationType));

            EntityMap<Entity, Entity> map = null;
            try {
                logger.info(String.format("Getting entityMap for source/destination: %s, %s", sourceType, destinationType));
                map = almanac.getMap(sourceType, destinationType);
            } catch (EntityMapNotFoundException ex) {
                throw new LookupException("Expected EntityMap", ex);
            }

            Entity destinationEntity = null;
            try {
                logger.info(String.format("Getting destination %s for source entity %s", destinationType, sourceEntity));
                destinationEntity = map.getDestinationFor(sourceEntity);
            } catch (EntityInstantiationException ex) {
                throw new LookupException("Expected entity destination", ex);
            }

            logger.info(String.format("Found destination entity %s for source entity %s", destinationEntity, sourceEntity));

            sourceEntity = destinationEntity;
        }

        return (Location) sourceEntity;
    }
}