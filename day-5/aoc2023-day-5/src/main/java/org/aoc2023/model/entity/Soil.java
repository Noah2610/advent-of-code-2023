package org.aoc2023.model.entity;

import org.aoc2023.model.Id;

public class Soil extends EntityBase {
    public Soil(Id id) {
        super(id);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.SOIL;
    }
}