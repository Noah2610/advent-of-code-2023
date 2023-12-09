package org.aoc2023.model;

public interface WithId {
    Id getId();

    int compare(WithId other);
}