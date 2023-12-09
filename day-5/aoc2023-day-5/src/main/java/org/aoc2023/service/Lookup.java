package org.aoc2023.service;

import org.aoc2023.exception.LookupException;
import org.aoc2023.model.Almanac;
import org.aoc2023.model.entity.Entity;

public interface Lookup<T extends Entity, R extends Entity> {
    R lookup(Almanac almanac, T entity) throws LookupException;
}