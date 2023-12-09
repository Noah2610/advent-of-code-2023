package org.aoc2023.model.entity;

import org.aoc2023.model.Id;

public class Water extends EntityBase {
    public Water(Id id) {
        super(id);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.WATER;
    }
}