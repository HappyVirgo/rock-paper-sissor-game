package com.al.qdt.cqrs.exceptions;

public class AggregateException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AggregateException(String message) {
        super(message);
    }
}
