package com.ryderbelserion.quckie.exceptions;

public class QuickieException extends IllegalStateException {

    public QuickieException(final String message, final Exception exception) {
        super(message, exception);
    }

    public QuickieException(final String message) {
        this(message, null);
    }
}