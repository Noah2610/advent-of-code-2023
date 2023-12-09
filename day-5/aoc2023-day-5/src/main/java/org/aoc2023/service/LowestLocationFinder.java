package org.aoc2023.service;

import org.aoc2023.exception.FinderException;
import org.aoc2023.exception.LookupException;
import org.aoc2023.model.Almanac;
import org.aoc2023.model.entity.Location;
import org.aoc2023.model.entity.Seed;

import java.util.Comparator;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private final Logger logger;

    public LowestLocationFinder(Lookup<Seed, Location> lookup) {
        this.lookup = lookup;
        this.logger = Logger.getLogger(LowestLocationFinder.class.getSimpleName());
        this.logger.setLevel(Level.OFF);
    }

    public Location find(Almanac almanac) throws FinderException {
//        Optional<Location> lowestLocationOpt = null;
        Location lowestLocation = null;

        try {
            for (var seedRange : almanac.getSeedsRanges()) {
                for (var seed : seedRange) {
                    Location location = lookup.lookup(almanac, seed);
                    if (lowestLocation == null || location.isSmallerThan(lowestLocation)) {
                        lowestLocation = location;
                    }
                }
            }

//            Stream<Location> locations = almanac.getSeedsRanges().stream()
//                    .flatMap(seedRange -> {
////                        logger.info(String.format("Flat mapping seed range: %s", seedRange));
//                        return StreamSupport.stream(seedRange.spliterator(), false);
//                    })
//                    .map(mapCatchEx(seed -> {
////                        logger.info(String.format("Looking up location for seed: %s", seed));
//                        return lookup.lookup(almanac, seed);
//                    }));
//
//            for (Iterator<Location> it = locations.iterator(); it.hasNext(); ) {
//                var location = it.next();
//                if (lowestLocation == null || location.isSmallerThan(lowestLocation)) {
//                    lowestLocation = location;
//                }
//            }

//                    .min(compareCatchEx((a, b) -> {
//                        logger.info(String.format("Comparing locations: %s and %s", a, b));
//                        return a.compare(b);
//                    }));
        } catch (RuntimeException ex) {
            throw new FinderException((LookupException) ex.getCause());
        } catch (LookupException e) {
            throw new FinderException(e);
        }

        if (lowestLocation == null) {
            throw new FinderException("Couldn't find closest location");
        }

        return lowestLocation;
        //return lowestLocationOpt.orElseThrow(() -> new FinderException("Couldn't find closest location"));
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