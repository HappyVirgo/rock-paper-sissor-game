package com.al.qdt.rps.cmd.api.advices;

import com.al.qdt.common.api.advices.GlobalExceptionHandler;
import com.al.qdt.common.api.errors.ApiError;
import com.al.qdt.rps.cmd.api.exceptions.GameException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

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
        final var apiError = new ApiError(e.getHttpStatus());
        apiError.setMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }
}
