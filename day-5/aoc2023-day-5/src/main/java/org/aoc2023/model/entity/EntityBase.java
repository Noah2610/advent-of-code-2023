package org.aoc2023.model.entity;

import org.aoc2023.model.Id;
import org.aoc2023.model.WithId;

import java.util.Objects;

public abstract class EntityBase implements Entity, WithId {
    private final Id id;

    protected EntityBase(Id id) {
        this.id = id;
    }

    @Override
    public Id getId() {
        return id;
    }

    @Override
    public int compare(WithId other) {
        return Long.compare(getId().id(), other.getId().id());
    }

    @Override
    public boolean isSmallerThan(WithId other) {
        return getId().id() < other.getId().id();
    }

    @Override
    public String toString() {
        return String.format("%s-%s", getClass().getSimpleName(), id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityBase that = (EntityBase) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}