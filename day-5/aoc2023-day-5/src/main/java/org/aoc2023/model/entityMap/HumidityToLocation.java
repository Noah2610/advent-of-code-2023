package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityMapCreationException;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Humidity;
import org.aoc2023.model.entity.Location;

import java.util.List;

public class HumidityToLocation extends EntityMapBase<Humidity, Location> {
    public HumidityToLocation(List<EntityMapConfig> maps) throws EntityMapCreationException {
        super(maps, Humidity.class, Location.class);
    }

    public HumidityToLocation() {
        super(Humidity.class, Location.class);
    }

    @Override
    public EntityMapType getEntityMapType() {
        return new EntityMapType(EntityType.HUMIDITY, EntityType.LOCATION);
    }
}