package org.aoc2023.model.entity;

import org.aoc2023.model.Id;

public class Fertilizer extends EntityBase {
    public Fertilizer(Id id) {
        super(id);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.FERTILIZER;
    }
}