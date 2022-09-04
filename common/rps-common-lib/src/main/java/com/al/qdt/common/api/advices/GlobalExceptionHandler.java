package com.al.qdt.common.api.advices;

import com.al.qdt.common.api.errors.ApiError;
import org.springframework.http.ResponseEntity;

/**
 * This interface contains ExceptionHandler utility methods.
 */
public interface GlobalExceptionHandler {

    default ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
