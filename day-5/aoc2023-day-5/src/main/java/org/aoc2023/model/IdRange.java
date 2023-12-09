package org.aoc2023.model;

import java.util.Iterator;
import java.util.Objects;

public class IdRange implements Range<Id> {
    private final Id start;
    private final Id end;

    public IdRange(Id start, Id end) {
        this.start = start;
        this.end = end;
    }

    public IdRange(Id start, long range) {
        this(start, Id.of(start.id() + range));
    }

    @Override
    public Id start() {
        return start;
    }

    @Override
    public Id end() {
        return end;
    }

    @Override
    public boolean contains(Id id) {
        return id.id() >= start.id() && id.id() < end.id();
    }

    @Override
    public boolean intersects(Range<Id> range) {
        return (contains(range.start())
                || contains(range.end())
                || range.contains(start())
                || range.contains(end()));
    }

    @Override
    public Id offsetOf(Id id) {
        return Id.of(id.id() - start().id());
    }

    @Override
    public Id withOffset(Id offset) {
        return Id.of(start().id() + offset.id());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdRange idRange = (IdRange) o;
        return Objects.equals(start, idRange.start) && Objects.equals(end, idRange.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return String.format("start=%s, end=%s", start(), end());
    }

    @Override
    public Iterator<Id> iterator() {
        return new Iterator<>() {
            private Id offset = start();

            @Override
            public boolean hasNext() {
                return offset.id() + 1 < end().id();
            }

            @Override
            public Id next() {
                offset = Id.of(offset.id() + 1);
                return offset;
            }
        };
    }
}