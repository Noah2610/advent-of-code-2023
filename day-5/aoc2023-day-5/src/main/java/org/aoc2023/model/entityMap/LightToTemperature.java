package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Light;
import org.aoc2023.model.entity.Temperature;

import java.util.List;

public class LightToTemperature extends EntityMapBase<Light, Temperature> {
    public LightToTemperature(List<EntityMapConfig> maps) throws EntityInstantiationException {
        super(maps, Light.class, Temperature.class);
    }

    public LightToTemperature() {
        super(Light.class, Temperature.class);
    }

    @Override
    public EntityMapType getEntityMapType() {
        return new EntityMapType(EntityType.LIGHT, EntityType.TEMPERATURE);
    }
}