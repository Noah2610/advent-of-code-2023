package org.aoc2023.exception;

import java.lang.reflect.InvocationTargetException;

public class EntityMapCreationException extends Exception {
    public EntityMapCreationException(ReflectiveOperationException ex) {
        super("Failed to create entity map: ", ex);
    }

    public EntityMapCreationException(NoSuchMethodException ex) {
        super("Failed to create entity map: ", ex);
    }

    public EntityMapCreationException(InvocationTargetException ex) {
        super("Failed to create entity map: ", ex);
    }

    public EntityMapCreationException(InstantiationException ex) {
        super("Failed to create entity map: ", ex);
    }

    public EntityMapCreationException(IllegalAccessException ex) {
        super("Failed to create entity map: ", ex);
    }
}