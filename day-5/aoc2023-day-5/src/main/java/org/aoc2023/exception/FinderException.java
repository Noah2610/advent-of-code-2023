package org.aoc2023.exception;

public class FinderException extends Exception {
    public FinderException(String msg) {
        super(msg);
    }

    public FinderException(LookupException ex) {
        super(ex);
    }

    public FinderException(String msg, LookupException ex) {
        super(msg, ex);
    }

    @Override
    public String toString() {
        Throwable cause = getCause();
        if (cause == null) {
            return String.format("Finder failed: %s", getMessage());
        } else {
            return String.format("Finder failed: %s, caused by: %s", getMessage(), getCause());
        }
    }
}