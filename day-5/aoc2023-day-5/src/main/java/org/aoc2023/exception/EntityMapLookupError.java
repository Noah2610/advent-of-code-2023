package org.aoc2023.exception;

import org.aoc2023.model.entity.Entity;

public class EntityMapLookupError extends Exception {
    public <Src extends Entity, Dst extends Entity> EntityMapLookupError(Class<Src> src, Class<Dst> dst, ReflectiveOperationException ex) {
        super(String.format("Couldn't find EntityMap from source %s to destination %s", src.getName(), dst.getName()), ex);
    }
}