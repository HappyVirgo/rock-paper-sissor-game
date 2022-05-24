package com.al.qdt.score.qry.advices;

import com.al.qdt.common.advices.GlobalExceptionHandler;
import com.al.qdt.common.errors.ApiError;
import com.al.qdt.score.qry.exceptions.ScoreNotFoundException;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Exception handler class for the {@link ScoreNotFoundException} custom exception.
 */
@Order(HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ScoreNotFoundExceptionHandler extends ResponseEntityExceptionHandler implements GlobalExceptionHandler {

    /**
     * Handle ScoreNotFoundException.
     *
     * @param e the ScoreNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(ScoreNotFoundException.class)
    protected ResponseEntity<Object> handleScoreNotFoundException(
            ScoreNotFoundException e) {
        final var apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }
}
