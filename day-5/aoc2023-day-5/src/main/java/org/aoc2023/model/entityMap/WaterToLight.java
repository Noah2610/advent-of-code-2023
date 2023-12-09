package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityMapCreationException;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Light;
import org.aoc2023.model.entity.Water;

import java.util.List;

public class WaterToLight extends EntityMapBase<Water, Light> {
    public WaterToLight(List<EntityMapConfig> maps) throws EntityMapCreationException {
        super(maps, Water.class, Light.class);
    }

    public WaterToLight() {
        super(Water.class, Light.class);
    }

    @Override
    public EntityMapType getEntityMapType() {
        return new EntityMapType(EntityType.WATER, EntityType.LIGHT);
    }
}