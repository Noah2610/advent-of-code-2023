package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Fertilizer;
import org.aoc2023.model.entity.Water;

import java.util.List;

public class FertilizerToWater extends EntityMapBase<Fertilizer, Water> {
    public FertilizerToWater(List<EntityMapConfig> maps) throws EntityInstantiationException {
        super(maps, Fertilizer::new, Water::new);
    }

    public FertilizerToWater() {
        super(Fertilizer::new, Water::new);
    }

    @Override
    public EntityMapType getEntityMapType() {
        return new EntityMapType(EntityType.FERTILIZER, EntityType.WATER);
    }
}