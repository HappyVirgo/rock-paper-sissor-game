package com.al.qdt.common.advices;

import com.al.qdt.common.errors.ApiError;
import org.springframework.http.ResponseEntity;

/**
 * This interface contains ExceptionHandler utility methods.
 */
public interface GlobalExceptionHandler {

    default ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
