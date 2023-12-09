package org.aoc2023.exception;

public class LookupException extends Exception {
    public LookupException(String msg, Throwable ex) {
        super(msg, ex);
    }

    public LookupException(String msg) {
        super(msg);
    }

    public LookupException(LookupException ex) {
        this(ex.getMessage(), ex.getCause());
    }

    @Override
    public String toString() {
        Throwable cause = getCause();
        if (cause == null) {
            return String.format("Lookup failed: %s", getMessage());
        } else {
            return String.format("Lookup failed: %s, caused by: %s", getMessage(), getCause());
        }
    }
}