package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Light;
import org.aoc2023.model.entity.Water;

import java.util.List;

public class WaterToLight extends EntityMapBase<Water, Light> {
    public WaterToLight(List<EntityMapConfig> maps) throws EntityInstantiationException {
        super(maps, Water::new, Light::new);
    }

    public WaterToLight() {
        super(Water::new, Light::new);
    }

    @Override
    public EntityMapType getEntityMapType() {
        return new EntityMapType(EntityType.WATER, EntityType.LIGHT);
    }
}