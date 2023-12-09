package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.model.entity.EntityType;
import org.aoc2023.model.entity.Seed;
import org.aoc2023.model.entity.Soil;

import java.util.List;

public class SeedToSoil extends EntityMapBase<Seed, Soil> {
    public SeedToSoil(List<EntityMapConfig> maps) throws EntityInstantiationException {
        super(maps, Seed::new, Soil::new);
    }

    public SeedToSoil() {
        super(Seed::new, Soil::new);
    }

    @Override
    public EntityMapType getEntityMapType() {
        return new EntityMapType(EntityType.SEED, EntityType.SOIL);
    }
}