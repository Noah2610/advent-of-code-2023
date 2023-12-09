package org.aoc2023.exception;

import java.lang.reflect.InvocationTargetException;

public class EntityInstantiationException extends Exception {
    public EntityInstantiationException(ReflectiveOperationException ex) {
        super("Failed to create entity map: ", ex);
    }

    public EntityInstantiationException(NoSuchMethodException ex) {
        super("Failed to create entity map: ", ex);
    }

    public EntityInstantiationException(InvocationTargetException ex) {
        super("Failed to create entity map: ", ex);
    }

    public EntityInstantiationException(InstantiationException ex) {
        super("Failed to create entity map: ", ex);
    }

    public EntityInstantiationException(IllegalAccessException ex) {
        super("Failed to create entity map: ", ex);
    }
}