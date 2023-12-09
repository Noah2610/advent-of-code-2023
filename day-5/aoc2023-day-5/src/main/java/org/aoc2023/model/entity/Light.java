package org.aoc2023.model.entity;

import org.aoc2023.model.Id;

public class Light extends EntityBase {
    public Light(Id id) {
        super(id);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.LIGHT;
    }
}