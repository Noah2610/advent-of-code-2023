package org.aoc2023.model.entity;

import org.aoc2023.model.Id;

public class Humidity extends EntityBase {
    public Humidity(Id id) {
        super(id);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.HUMIDITY;
    }
}