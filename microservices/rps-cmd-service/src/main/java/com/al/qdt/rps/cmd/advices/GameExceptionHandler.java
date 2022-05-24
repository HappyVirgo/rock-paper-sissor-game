package com.al.qdt.rps.cmd.advices;

import com.al.qdt.common.advices.GlobalExceptionHandler;
import com.al.qdt.common.errors.ApiError;
import com.al.qdt.rps.cmd.exceptions.GameException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Exception handler class for the {@link GameException} custom exception.
 */
@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GameExceptionHandler extends ResponseEntityExceptionHandler implements GlobalExceptionHandler {

    /**
     * Handle GameException.
     *
     * @param e the GameException
     * @return the ApiError object
     */
    @ExceptionHandler(GameException.class)
    protected ResponseEntity<Object> handleGameException(
            GameException e) {
        final var apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }
}
