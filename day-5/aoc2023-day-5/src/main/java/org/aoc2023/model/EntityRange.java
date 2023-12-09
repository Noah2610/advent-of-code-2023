package org.aoc2023.model;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.model.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Objects;

public class EntityRange<T extends Entity> implements Range<T> {
    private final T start;
    private final T end;
    private final Class<T> entityClass;

    public EntityRange(T start, T end, Class<T> entityClass) {
        this.start = start;
        this.end = end;
        this.entityClass = entityClass;
    }

    public EntityRange(T start, long range, Class<T> entityClass) throws EntityInstantiationException {
        this(start, newEntity(Id.of(start.getId().id() + range), entityClass), entityClass);
    }

    @Override
    public T start() {
        return start;
    }

    @Override
    public T end() {
        return end;
    }

    @Override
    public boolean contains(T entity) {
        return entity.getId().id() >= start.getId().id() && entity.getId().id() < end.getId().id();
    }

    @Override
    public boolean intersects(Range<T> range) {
        return (contains(range.start())
                || contains(range.end())
                || range.contains(start())
                || range.contains(end()));
    }

    @Override
    public T offsetOf(T entity) throws RuntimeException {
        try {
            return newEntity(Id.of(entity.getId().id() - start().getId().id()), entityClass);
        } catch (EntityInstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T withOffset(T offset) throws RuntimeException {
        try {
            return newEntity(Id.of(start().getId().id() + offset.getId().id()), entityClass);
        } catch (EntityInstantiationException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<>() {
            private T offset = start();

            @Override
            public boolean hasNext() {
                return offset.getId().id() + 1 < end().getId().id();
            }

            @Override
            public T next() throws RuntimeException {
                try {
                    offset = newEntity(Id.of(offset.getId().id() + 1), entityClass);
                } catch (EntityInstantiationException e) {
                    throw new RuntimeException(e);
                }
                return offset;
            }
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityRange<?> that = (EntityRange<?>) o;
        return Objects.equals(start, that.start) && Objects.equals(end, that.end) && Objects.equals(entityClass, that.entityClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end, entityClass);
    }

    private static <T> T newEntity(Id id, Class<T> clazz) throws EntityInstantiationException {
        try {
            return clazz.getDeclaredConstructor(Id.class).newInstance(id);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException ex) {
            throw new EntityInstantiationException(ex);
        }
    }

    @Override
    public String toString() {
        return String.format("start=%s, end=%s", start(), end());
    }
}