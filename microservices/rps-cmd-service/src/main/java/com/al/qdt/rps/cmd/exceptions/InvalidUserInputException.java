package com.al.qdt.rps.cmd.exceptions;

/**
 * This exception class contains Rock Paper Scissor game custom exception.
 */
public class InvalidUserInputException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // exception message
    public static final String INVALID_USER_INPUT_EXCEPTION_TEXT = "Invalid user input!";

    public InvalidUserInputException() {
        super(INVALID_USER_INPUT_EXCEPTION_TEXT);
    }
}
