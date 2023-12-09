package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Humidity;
import org.aoc2023.model.entity.Location;

import java.util.List;

public class HumidityToLocation extends EntityMapBase<Humidity, Location> {
    public HumidityToLocation(List<EntityMapConfig> maps) throws EntityInstantiationException {
        super(maps, Humidity::new, Location::new);
    }

    public HumidityToLocation() {
        super(Humidity::new, Location::new);
    }

    @Override
    public EntityMapType getEntityMapType() {
        return new EntityMapType(EntityType.HUMIDITY, EntityType.LOCATION);
    }
}