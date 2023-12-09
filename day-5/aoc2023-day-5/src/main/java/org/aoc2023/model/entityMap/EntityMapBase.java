package org.aoc2023.model.entityMap;

import org.aoc2023.exception.EntityMapCreationException;
import org.aoc2023.model.Id;
import org.aoc2023.model.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class EntityMapBase<Src extends Entity, Dst extends Entity> implements EntityMap<Src, Dst> {
    private Map<Src, Dst> entityMap;
    private final Class<Src> srcClass;
    private final Class<Dst> dstClass;

    protected EntityMapBase(
            List<EntityMapConfig> maps,
            Class<Src> srcClass,
            Class<Dst> dstClass
    ) throws EntityMapCreationException {
        this.entityMap = new HashMap();
        this.srcClass = srcClass;
        this.dstClass = dstClass;
        addMaps(maps);
    }

    protected EntityMapBase(
            Class<Src> srcClass,
            Class<Dst> dstClass
    ) {
        this.entityMap = new HashMap();
        this.srcClass = srcClass;
        this.dstClass = dstClass;
    }

    @Override
    public void addMaps(List<EntityMapConfig> maps) throws EntityMapCreationException {
        for (var map : maps) {
            addMap(map);
        }
    }

    @Override
    public void addMap(EntityMapConfig map) throws EntityMapCreationException {
        for (int i = 0; i < map.range(); i++) {
            Src src = newSrc(new Id(map.source() + i));
            Dst dst = newDst(new Id(map.destination() + i));
            entityMap.put(src, dst);
        }
    }

    @Override
    public Dst getDestinationFor(Src src) throws EntityMapCreationException {
        Dst dst = entityMap.get(src);
        if (dst == null) {
            return newDst(src.getId());
        }
        return dst;
    }

    private Src newSrc(Id id) throws EntityMapCreationException {
        return newEntity(id, srcClass);

    }

    private Dst newDst(Id id) throws EntityMapCreationException {
        return newEntity(id, dstClass);
    }

    private <T extends Entity> T newEntity(Id id, Class<T> clazz) throws EntityMapCreationException {
        try {
            return clazz.getDeclaredConstructor(Id.class).newInstance(id);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException ex) {
            throw new EntityMapCreationException(ex);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EntityMapBase<?, ?> that = (EntityMapBase<?, ?>) o;
        return Objects.equals(entityMap, that.entityMap) && Objects.equals(srcClass, that.srcClass) && Objects.equals(dstClass, that.dstClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(entityMap, srcClass, dstClass);
    }

    @Override
    public String toString() {
        return entityMap.toString();
    }
}