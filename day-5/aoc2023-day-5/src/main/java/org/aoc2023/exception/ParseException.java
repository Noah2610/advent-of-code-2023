package org.aoc2023.exception;

public class ParseException extends Exception {
    public ParseException(String msg) {
        super(newErrorMessage(msg));
    }

    public <T> ParseException(Class<T> resultType) {
        super(newErrorMessage(resultType));
    }

    public ParseException(String msg, EntityMapCreationException ex) {
        super(newErrorMessage(msg), ex);
    }

    public ParseException(String msg, ParseException ex) {
        super(newErrorMessage(msg), ex);
    }

    public <T> ParseException(Class<T> resultType, EntityMapCreationException ex) {
        super(newErrorMessage(resultType), ex);
    }

    private static String newErrorMessage(String msg) {
        return String.format("Failed to parse: %s", msg);
    }

    private static <T> String newErrorMessage(Class<T> resultType) {
        return newErrorMessage(resultType.getName());
    }

    @Override
    public String toString() {
        Throwable cause = getCause();
        if (cause == null) {
            return getMessage();
        } else {
            return String.format("%s, caused by: %s", getMessage(), cause);
        }
    }
}