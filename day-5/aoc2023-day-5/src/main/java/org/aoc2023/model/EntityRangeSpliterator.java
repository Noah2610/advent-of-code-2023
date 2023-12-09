package org.aoc2023.model;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.model.entity.Entity;

import java.util.Spliterator;
import java.util.function.Consumer;

interface EntityCreator<T extends Entity> {
    T create(Id id) throws EntityInstantiationException;
}

public class EntityRangeSpliterator<T extends Entity> implements Spliterator<T> {
    private final EntityRange<T> range;
    private final EntityCreator<T> entityCreator;
    private long origin;
    private final long fence;

    public EntityRangeSpliterator(EntityRange<T> range, EntityCreator<T> entityCreator) {
        this(range, entityCreator, 1, range.end().getId().id());
    }

    private EntityRangeSpliterator(
            EntityRange<T> range,
            EntityCreator<T> entityCreator,
            long origin,
            long fence
    ) {
        this.range = range;
        this.entityCreator = entityCreator;
        this.origin = origin;
        this.fence = fence;
    }

    @Override
    public void forEachRemaining(Consumer<? super T> action) {
        while (tryAdvance(action)) {
        }
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action) throws RuntimeException {
        if (origin >= fence) {
            return false;
        }

        T entity;
        try {
            entity = entityCreator.create(Id.of(origin));
        } catch (EntityInstantiationException e) {
            throw new RuntimeException(e);
        }

        action.accept(entity);
        origin++;
        return true;
    }

    @Override
    public Spliterator<T> trySplit() {
        long lo = origin;
        long mid = ((lo + fence) >>> 1) & ~1;

        if (lo >= mid) {
            return null;
        }

        origin = mid;
        return new EntityRangeSpliterator<T>(range, entityCreator, lo, mid);
    }

    @Override
    public long estimateSize() {
        return ((fence - origin) / 2);
    }

    @Override
    public int characteristics() {
        return ORDERED | IMMUTABLE;
    }
}