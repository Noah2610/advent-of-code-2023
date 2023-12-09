package org.aoc2023.model;

public interface Range<T> extends Iterable<T> {
    T start();

    T end();

    boolean contains(T t);

    boolean intersects(Range<T> range);

    T offsetOf(T t);

    T withOffset(T offset);
}