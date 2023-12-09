package org.aoc2023.model;

import org.aoc2023.exception.EntityMapNotFoundException;
import org.aoc2023.model.entity.Entity;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Seed;
import org.aoc2023.model.entityMap.EntityMap;
import org.aoc2023.model.entityMap.EntityMapType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Almanac {
    private final List<Seed> seeds;
    private final Map<EntityMapType, EntityMap<?, ?>> maps;

    public Almanac(List<Seed> seeds, List<EntityMap<?, ?>> maps) {
        this.seeds = seeds;
        this.maps = new HashMap<>();

        for (var map : maps) {
            this.maps.put(map.getEntityMapType(), map);
        }
    }

    public List<Seed> getSeeds() {
        return seeds;
    }

    public <Src extends Entity, Dst extends Entity> EntityMap<Src, Dst> getMap(
            EntityType sourceType,
            EntityType destinationType
    ) throws EntityMapNotFoundException {
        EntityMapType mapType = new EntityMapType(sourceType, destinationType);
        if (mapType == null) {
            throw new EntityMapNotFoundException(sourceType, destinationType);
        }

        return (EntityMap<Src, Dst>) maps.get(mapType);

        // TODO
//        try {
//            EntityType sourceEntityType = (EntityType) sourceClass.getDeclaredMethod("getEntityType").invoke((Src) new Object());
//            EntityType destinationEntityType = (EntityType) destinationClass.getDeclaredMethod("getEntityType").invoke((Dst) new Object());
//            EntityMapType mapType = new EntityMapType(sourceEntityType, destinationEntityType);
//            return (EntityMap<Src, Dst>) maps.get(mapType);
//        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
//            throw new EntityMapLookupError(sourceClass, destinationClass, ex);
//        }
    }

    @Override
    public String toString() {
        return String.format(
                "Seeds: %s\nEntityMaps: %s",
                seeds,
                Arrays.toString(maps.entrySet().toArray())
        );
    }
}