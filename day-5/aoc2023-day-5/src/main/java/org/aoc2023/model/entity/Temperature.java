package org.aoc2023.model.entity;

import org.aoc2023.model.Id;

public class Temperature extends EntityBase {
    public Temperature(Id id) {
        super(id);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.TEMPERATURE;
    }
}