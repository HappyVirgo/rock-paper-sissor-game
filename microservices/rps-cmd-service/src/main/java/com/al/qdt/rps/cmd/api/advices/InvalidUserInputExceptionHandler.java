package com.al.qdt.rps.cmd.api.advices;

import com.al.qdt.common.api.advices.GlobalExceptionHandler;
import com.al.qdt.common.api.errors.ApiError;
import com.al.qdt.rps.cmd.api.exceptions.InvalidUserInputException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Exception handler class for the {@link InvalidUserInputException} custom exception.
 */
@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class InvalidUserInputExceptionHandler extends ResponseEntityExceptionHandler implements GlobalExceptionHandler {

    /**
     * Handle InvalidUserInputException.
     *
     * @param e the InvalidUserInputException
     * @return the ApiError object
     */
    @ExceptionHandler(InvalidUserInputException.class)
    protected ResponseEntity<Object> handleInvalidUserInputException(
            InvalidUserInputException e) {
        final var apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }
}
