package com.al.qdt.common.api.advices;

import com.al.qdt.common.api.errors.ApiError;
import com.al.qdt.common.exceptions.DispatcherException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.util.Objects;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNSUPPORTED_MEDIA_TYPE;

/**
 * Global exception handler class for the standard and custom exceptions.
 */
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalRestExceptionHandler extends ResponseEntityExceptionHandler implements GlobalExceptionHandler {
    private static final String MALFORMED_JSON_EXCEPTION_MESSAGE = "Malformed JSON request";
    private static final String VALIDATION_EXCEPTION_MESSAGE = "Validation error";
    private static final String MESSAGE_NOT_WRITABLE_EXCEPTION_MESSAGE = "Error writing JSON output";
    private static final String DATABASE_EXCEPTION_MESSAGE = "Database error";
    private static final String TYPE_MISMATCH_EXCEPTION_MESSAGE = "The parameter '%s' of value '%s' could not be converted to type '%s'";
    private static final String NO_HANDLER_FOUND_EXCEPTION_MESSAGE = "Could not find the %s method for URL %s";
    private static final String PARAM_MISSING_MESSAGE = "parameter is missing";
    private static final String MEDIA_TYPE_MESSAGE = " media type is not supported. Supported media types are ";

    /**
     * Handle MissingServletRequestParameterException. Triggered when a 'required' request parameter is missing.
     *
     * @param e       MissingServletRequestParameterException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException e,
                                                                          HttpHeaders headers,
                                                                          HttpStatus status,
                                                                          WebRequest request) {
        return buildResponseEntity(new ApiError(BAD_REQUEST, String.format("%s %s", e.getParameterName(), PARAM_MISSING_MESSAGE), e));
    }

    /**
     * Handle HttpMediaTypeNotSupportedException. This one triggers when JSON is invalid as well.
     *
     * @param e       HttpMediaTypeNotSupportedException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException e,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        final var builder = new StringBuilder();
        builder.append(e.getContentType());
        builder.append(MEDIA_TYPE_MESSAGE);
        e.getSupportedMediaTypes().forEach(t -> builder.append(t).append(", "));
        return buildResponseEntity(new ApiError(UNSUPPORTED_MEDIA_TYPE, builder.substring(0, builder.length() - 2), e));
    }

    /**
     * Handle MethodArgumentNotValidException. Triggered when an object fails @Valid validation.
     *
     * @param e       the MethodArgumentNotValidException that is thrown when @Valid validation fails
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        final var apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(VALIDATION_EXCEPTION_MESSAGE);
        apiError.addValidationErrors(e.getBindingResult().getFieldErrors());
        apiError.addValidationError(e.getBindingResult().getGlobalErrors());
        return buildResponseEntity(apiError);
    }

    /**
     * Handle HttpMessageNotReadableException. Happens when request JSON is malformed.
     *
     * @param e       HttpMessageNotReadableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        final var servletWebRequest = (ServletWebRequest) request;
        log.info("{} to {}", servletWebRequest.getHttpMethod(), servletWebRequest.getRequest().getServletPath());
        return buildResponseEntity(new ApiError(BAD_REQUEST, MALFORMED_JSON_EXCEPTION_MESSAGE, e));
    }

    /**
     * Handle HttpMessageNotWritableException.
     *
     * @param e       HttpMessageNotWritableException
     * @param headers HttpHeaders
     * @param status  HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException e,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, MESSAGE_NOT_WRITABLE_EXCEPTION_MESSAGE, e));
    }

    /**
     * Handle NoHandlerFoundException.
     *
     * @param e the NoHandlerFoundException
     * @param headers HttpHeaders
     * @param status HttpStatus
     * @param request WebRequest
     * @return the ApiError object
     */
    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException e,
                                                                   HttpHeaders headers,
                                                                   HttpStatus status,
                                                                   WebRequest request) {
        final var apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(String.format(NO_HANDLER_FOUND_EXCEPTION_MESSAGE, e.getHttpMethod(), e.getRequestURL()));
        apiError.setDebugMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles javax.validation.ConstraintViolationException. Thrown when @Validated fails.
     *
     * @param e the ConstraintViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(javax.validation.ConstraintViolationException.class)
    protected ResponseEntity<Object> handleConstraintViolation(javax.validation.ConstraintViolationException e) {
        final var apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(VALIDATION_EXCEPTION_MESSAGE);
        apiError.addValidationErrors(e.getConstraintViolations());
        return buildResponseEntity(apiError);
    }

    /**
     * Handles EntityNotFoundException. Created to encapsulate errors with more detail than javax.persistence.EntityNotFoundException.
     *
     * @param e the EntityNotFoundException
     * @return the ApiError object
     */
    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException e) {
        final var apiError = new ApiError(NOT_FOUND);
        apiError.setMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handle DataIntegrityViolationException, inspects the cause for different DB causes.
     *
     * @param e the DataIntegrityViolationException
     * @return the ApiError object
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<Object> handleDataIntegrityViolation(DataIntegrityViolationException e,
                                                                  WebRequest request) {
        if (e.getCause() instanceof ConstraintViolationException) {
            return buildResponseEntity(new ApiError(CONFLICT, DATABASE_EXCEPTION_MESSAGE, e.getCause()));
        }
        return buildResponseEntity(new ApiError(INTERNAL_SERVER_ERROR, e));
    }

    /**
     * Handle Exception, handle generic Exception.class
     *
     * @param e the Exception
     * @return the ApiError object
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e,
                                                                      WebRequest request) {
        final var apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(String.format(TYPE_MISMATCH_EXCEPTION_MESSAGE, e.getName(), e.getValue(), Objects.requireNonNull(e.getRequiredType()).getSimpleName()));
        apiError.setDebugMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }

    /**
     * Handle DispatcherException.
     *
     * @param e the DispatcherException
     * @return the ApiError object
     */
    @ExceptionHandler(DispatcherException.class)
    protected ResponseEntity<Object> handleDispatcherException(
            DispatcherException e) {
        final var apiError = new ApiError(BAD_REQUEST);
        apiError.setMessage(e.getMessage());
        return buildResponseEntity(apiError);
    }
}
