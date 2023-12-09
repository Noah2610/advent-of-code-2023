package org.aoc2023.exception;

import org.aoc2023.model.entity.EntityType;

public class EntityMapNotFoundException extends Exception {
    public EntityMapNotFoundException(EntityType sourceType, EntityType destinationType) {
        super(String.format("Entity map not found for source/destination: %s/%s", sourceType, destinationType));
    }
}