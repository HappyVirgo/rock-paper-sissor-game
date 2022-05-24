package com.al.qdt.rps.qry.exceptions;

/**
 * This exception class contains Rock Paper Scissor Query service custom exception.
 */
public class GameNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    // exception message
    public static final String GAME_BY_ID_NOT_FOUND_EXCEPTION_MESSAGE = "Game with id %s has not been found!";
    // exception message
    public static final String GAME_BY_USERNAME_NOT_FOUND_EXCEPTION_MESSAGE = "Games with username %s have not been found!";
    // exception message
    public static final String GAMES_NOT_FOUND_EXCEPTION_MESSAGE = "Games have not been found!";

    public GameNotFoundException(String message) {
        super(message);
    }
}
