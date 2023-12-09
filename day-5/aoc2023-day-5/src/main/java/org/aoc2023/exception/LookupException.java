package org.aoc2023.exception;

public class LookupException extends Exception {
    public LookupException(String msg, Throwable ex) {
        super(msg, ex);
    }

    @Override
    public String toString() {
        return String.format("Lookup failed: %s, caused by: %s", getMessage(), getCause());
    }
}