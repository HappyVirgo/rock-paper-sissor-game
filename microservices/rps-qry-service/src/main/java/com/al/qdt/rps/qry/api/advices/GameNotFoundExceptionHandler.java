package com.al.qdt.rps.qry.api.advices;

import com.al.qdt.common.api.advices.GlobalExceptionHandler;
import com.al.qdt.common.api.errors.ApiError;
import com.al.qdt.rps.qry.api.exceptions.GameNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Exception handler class for the {@link GameNotFoundException} custom exception.
 */
@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GameNotFoundExceptionHandler extends ResponseEntityExceptionHandler implements GlobalExceptionHandler {

    /**
     * Handle GameNotFoundException.
     *
     * @param e the GameNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(GameNotFoundException.class)
    protected ResponseEntity<Object> handleGameNotFoundExceptionHandler(
            GameNotFoundException e) {
        final var apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }
}
