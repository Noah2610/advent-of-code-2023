package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.model.entity.Entity;

import java.util.List;

public interface EntityMap<Src extends Entity, Dst extends Entity> {
    void addMaps(List<EntityMapConfig> maps) throws EntityInstantiationException;

    void addMap(EntityMapConfig map) throws EntityInstantiationException;

    Dst getDestinationFor(Src src) throws EntityInstantiationException;

    EntityMapType getEntityMapType();
}