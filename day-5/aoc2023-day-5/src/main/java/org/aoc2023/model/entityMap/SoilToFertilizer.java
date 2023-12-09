package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityMapCreationException;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Fertilizer;
import org.aoc2023.model.entity.Soil;

import java.util.List;

public class SoilToFertilizer extends EntityMapBase<Soil, Fertilizer> {
    public SoilToFertilizer(List<EntityMapConfig> maps) throws EntityMapCreationException {
        super(maps, Soil.class, Fertilizer.class);
    }

    public SoilToFertilizer() {
        super(Soil.class, Fertilizer.class);
    }

    @Override
    public EntityMapType getEntityMapType() {
        return new EntityMapType(EntityType.SOIL, EntityType.FERTILIZER);
    }
}