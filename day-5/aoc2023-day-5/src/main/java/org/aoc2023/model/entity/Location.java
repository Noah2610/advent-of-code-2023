package org.aoc2023.model.entity;

import org.aoc2023.model.Id;

public class Location extends EntityBase {
    public Location(Id id) {
        super(id);
    }

    @Override
    public EntityType getEntityType() {
        return EntityType.LOCATION;
    }
}