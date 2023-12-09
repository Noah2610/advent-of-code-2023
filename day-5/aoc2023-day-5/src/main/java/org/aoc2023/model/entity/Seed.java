package org.aoc2023.model.entity;

import org.aoc2023.model.Id;

public class Seed extends EntityBase {
    public Seed(Id id) {
        super(id);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.SEED;
    }
}