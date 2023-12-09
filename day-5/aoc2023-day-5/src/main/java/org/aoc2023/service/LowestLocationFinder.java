package org.aoc2023.service;

import org.aoc2023.exception.FinderException;
import org.aoc2023.exception.LookupException;
import org.aoc2023.model.Almanac;
import org.aoc2023.model.entity.Location;
import org.aoc2023.model.entity.Seed;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

@FunctionalInterface
interface ThrowableComparator<T> {
    int compare(T a, T b) throws LookupException;
}

@FunctionalInterface
interface ThrowableMapper<T, R> {
    R apply(T a) throws LookupException;
}

public class LowestLocationFinder implements Finder<Location> {
    private final Lookup<Seed, Location> lookup;

    public LowestLocationFinder(Lookup<Seed, Location> lookup) {
        this.lookup = lookup;
    }

    public Location find(Almanac almanac) throws FinderException {
        Optional<Location> lowestLocationOpt = null;
        try {
            lowestLocationOpt = almanac.getSeeds().stream()
                    .map(mapCatchEx(seed -> lookup.lookup(almanac, seed)))
                    .min(compareCatchEx((a, b) -> a.compare(b)));
        } catch (RuntimeException ex) {
            throw new FinderException((LookupException) ex.getCause());
        }

        return lowestLocationOpt.orElseThrow(() -> new FinderException("Couldn't find closest location"));
    }

    private <T> Comparator<T> compareCatchEx(ThrowableComparator<T> predicate) {
        return (a, b) -> {
            try {
                return predicate.compare(a, b);
            } catch (LookupException ex) {
                throw new RuntimeException(ex);
            }
        };
    }

    private <T, R> Function<T, R> mapCatchEx(ThrowableMapper<T, R> predicate) {
        return (a) -> {
            try {
                return predicate.apply(a);
            } catch (LookupException ex) {
                throw new RuntimeException(ex);
            }
        };
    }
}