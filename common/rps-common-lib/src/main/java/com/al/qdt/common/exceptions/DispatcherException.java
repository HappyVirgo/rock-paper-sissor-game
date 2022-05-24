package com.al.qdt.common.exceptions;

/**
 * This exception class contains Dispatcher custom exception.
 */
public class DispatcherException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DispatcherException(String message) {
        super(message);
    }
}
