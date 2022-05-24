package com.al.qdt.score.qry.exceptions;

/**
 * This exception class contains Score Query service custom exception.
 */
public class ScoreNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // exception message
    public static final String SCORE_BY_ID_NOT_FOUND_EXCEPTION_MESSAGE = "Score with id %s has not been found!";
    // exception message
    public static final String SCORES_BY_WINNER_NOT_FOUND_EXCEPTION_MESSAGE = "Scores with winner type %s have not been found!";
    // exception message
    public static final String SCORES_NOT_FOUND_EXCEPTION_MESSAGE = "Scores have not been found!";

    public ScoreNotFoundException(String message) {
        super(message);
    }
}
