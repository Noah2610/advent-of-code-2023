package org.aoc2023.model;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Seed;
import org.aoc2023.model.entity.Soil;
import org.aoc2023.model.entityMap.EntityMap;
import org.aoc2023.model.entityMap.EntityMapBase;
import org.aoc2023.model.entityMap.EntityMapConfig;
import org.aoc2023.model.entityMap.EntityMapType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityMapBaseTest {
    @Test
    void createEntityMapBase() {
        List<EntityMapConfig> configs = List.of(
                new EntityMapConfig(0, 0, 1),
                new EntityMapConfig(10, 20, 10)
        );

        try {
            EntityMap<Seed, Soil> map = new EntityMapBase<>(configs, Seed::new, Soil::new) {
                @Override
                public EntityMapType getEntityMapType() {
                    return new EntityMapType(EntityType.SEED, EntityType.SOIL);
                }
            };

            assertEquals(new Soil(new Id(0)), map.getDestinationFor(new Seed(new Id(0))));
            assertEquals(new Soil(new Id(9)), map.getDestinationFor(new Seed(new Id(9))));
            assertEquals(new Soil(new Id(20)), map.getDestinationFor(new Seed(new Id(10))));
            assertEquals(new Soil(new Id(25)), map.getDestinationFor(new Seed(new Id(15))));
            assertEquals(new Soil(new Id(29)), map.getDestinationFor(new Seed(new Id(19))));
            assertEquals(new Soil(new Id(20)), map.getDestinationFor(new Seed(new Id(20))));
        } catch (EntityInstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}