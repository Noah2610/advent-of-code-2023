package org.aoc2023.model.entity;

import org.aoc2023.model.WithId;

public interface Entity extends WithId {
    EntityType getEntityType();
}