package org.aoc2023.service;

import org.aoc2023.exception.FinderException;
import org.aoc2023.model.Almanac;
import org.aoc2023.model.entity.Entity;

public interface Finder<T extends Entity> {
    T find(Almanac almanac) throws FinderException;
}