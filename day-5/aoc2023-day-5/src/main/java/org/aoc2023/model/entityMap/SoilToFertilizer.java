package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Fertilizer;
import org.aoc2023.model.entity.Soil;

import java.util.List;

public class SoilToFertilizer extends EntityMapBase<Soil, Fertilizer> {
    public SoilToFertilizer(List<EntityMapConfig> maps) throws EntityInstantiationException {
        super(maps, Soil::new, Fertilizer::new);
    }

    public SoilToFertilizer() {
        super(Soil::new, Fertilizer::new);
    }

    @Override
    public EntityMapType getEntityMapType() {
        return new EntityMapType(EntityType.SOIL, EntityType.FERTILIZER);
    }
}