package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityInstantiationException;
import org.aoc2023.model.Id;
import org.aoc2023.model.IdRange;
import org.aoc2023.model.Range;
import org.aoc2023.model.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class EntityMapBase<Src extends Entity, Dst extends Entity> implements EntityMap<Src, Dst> {
    private final Map<Range<Id>, Range<Id>> rangeMap;
    private final Class<Src> srcClass;
    private final Class<Dst> dstClass;

    protected EntityMapBase(
            List<EntityMapConfig> maps,
            Class<Src> srcClass,
            Class<Dst> dstClass
    ) throws EntityInstantiationException {
        this(srcClass, dstClass);
        addMaps(maps);
    }

    protected EntityMapBase(
            Class<Src> srcClass,
            Class<Dst> dstClass
    ) {
        this.rangeMap = new HashMap<>();
        this.srcClass = srcClass;
        this.dstClass = dstClass;
    }

    @Override
    public void addMaps(List<EntityMapConfig> maps) throws EntityInstantiationException {
        for (var map : maps) {
            addMap(map);
        }
    }

    @Override
    public void addMap(EntityMapConfig map) throws EntityInstantiationException {
        Range<Id> srcRange = new IdRange(Id.of(map.source()), map.range());
        Range<Id> dstRange = new IdRange(Id.of(map.destination()), map.range());
        rangeMap.put(srcRange, dstRange);
    }

    @Override
    public Dst getDestinationFor(Src src) throws EntityInstantiationException {
        Id dstId = rangeMap.entrySet().stream()
                .filter(entry -> entry.getKey().contains(src.getId()))
                .findFirst()
                .map(entry -> {
                    Id offset = entry.getKey().offsetOf(src.getId());
                    return entry.getValue().withOffset(offset);
                })
                .orElseGet(() -> src.getId());
        return newDst(dstId);
    }

    private Src newSrc(Id id) throws EntityInstantiationException {
        return newEntity(id, srcClass);

    }

    private Dst newDst(Id id) throws EntityInstantiationException {
        return newEntity(id, dstClass);
    }

    private <T extends Entity> T newEntity(Id id, Class<T> clazz) throws EntityInstantiationException {
        try {
            return clazz.getDeclaredConstructor(Id.class).newInstance(id);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException ex) {
            throw new EntityInstantiationException(ex);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityMapBase<?, ?> that = (EntityMapBase<?, ?>) o;
        return Objects.equals(rangeMap, that.rangeMap) && Objects.equals(srcClass, that.srcClass) && Objects.equals(dstClass, that.dstClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rangeMap, srcClass, dstClass);
    }

    @Override
    public String toString() {
        return rangeMap.toString();
    }
}