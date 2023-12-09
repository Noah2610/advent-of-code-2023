package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityMapCreationException;
import org.aoc2023.model.entity.Entity;

import java.util.List;

public interface EntityMap<Src extends Entity, Dst extends Entity> {
    void addMaps(List<EntityMapConfig> maps) throws EntityMapCreationException;

    void addMap(EntityMapConfig map) throws EntityMapCreationException;

    Dst getDestinationFor(Src src) throws EntityMapCreationException;

    EntityMapType getEntityMapType();
}