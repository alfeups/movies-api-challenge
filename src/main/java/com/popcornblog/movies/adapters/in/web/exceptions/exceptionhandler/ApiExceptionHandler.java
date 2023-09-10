package com.popcornblog.movies.adapters.in.web.exceptions.exceptionhandler;


import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.PropertyBindingException;
import com.popcornblog.movies.core.domain.exceptions.BusinessException;
import com.popcornblog.movies.core.domain.exceptions.EntityInUseException;
import com.popcornblog.movies.core.domain.exceptions.EntityNotFoundException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String GENERIC_ERROR_MESSAGE_USER = "An unexpected internal error occurred in the system. "
            + "Please try again, and if the issue persists, contact "
            + "the system administrator.";

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers,
                                                                  HttpStatusCode statusCode, WebRequest request) {
        Throwable rootCause = ExceptionUtils.getRootCause(ex);

        if (rootCause instanceof InvalidFormatException) {
            return handleInvalidFormat((InvalidFormatException) rootCause, headers, statusCode, request);
        } else if (rootCause instanceof PropertyBindingException){
            return handlePropertyBindingException((PropertyBindingException) rootCause, headers, statusCode, request);
        }

        ProblemType problemType = ProblemType.UNABLE_TO_READ_REQUEST_BODY;
        String detail = "Invalid request body. Check for syntax error.";

        Problem problem = createProblemBuilder(HttpStatus.valueOf(statusCode.value()), problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), statusCode, request);
    }

    private ResponseEntity<Object> handlePropertyBindingException(PropertyBindingException ex, HttpHeaders headers, HttpStatusCode statusCode,
                                                                WebRequest request) {
        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.UNABLE_TO_READ_REQUEST_BODY;
        String detail = String.format("The property %s does not exist. " +
                "Please correct or remove this property and try again.", path);

        Problem problem = createProblemBuilder(HttpStatus.valueOf(statusCode.value()), problemType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE_USER)
                .build();

        return handleExceptionInternal(ex, problem, headers, statusCode, request);
    }

    private ResponseEntity<Object> handleInvalidFormat(InvalidFormatException ex, HttpHeaders headers,
                                                       HttpStatusCode statusCode, WebRequest request) {
        String path = joinPath(ex.getPath());

        ProblemType problemType = ProblemType.UNABLE_TO_READ_REQUEST_BODY;
        String detail = String.format("The property '%s' received the value '%s', "
                        + "which is of an invalid type. Please correct it and provide a" +
                        " value compatible with the type %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        Problem problem = createProblemBuilder(HttpStatus.valueOf(statusCode.toString()), problemType, detail)
                .userMessage(GENERIC_ERROR_MESSAGE_USER)
                .build();

        return handleExceptionInternal(ex, problem, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatusCode statusCode, WebRequest request) {
        if (ex instanceof MethodArgumentTypeMismatchException) {
            return handleMethodArgumentTypeMismatch((MethodArgumentTypeMismatchException) ex, headers,
                    HttpStatus.valueOf(statusCode.value()), request);
        }
        return super.handleTypeMismatch(ex, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                                   HttpStatusCode statusCode, WebRequest request) {
        if (ex instanceof NoHandlerFoundException) {
            return handleNotFoundException((NoHandlerFoundException) ex, headers, statusCode, request);
        }
            return super.handleNoHandlerFoundException(ex, headers, statusCode, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode statusCode,
                                                                  WebRequest request) {

        List<String> invalidFields = new ArrayList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            String field = fieldError.getField();
            String errorMessage = fieldError.getDefaultMessage();
            invalidFields.add(field + ": " + errorMessage);
        }

        ProblemType problemType = ProblemType.INVALID_DATA;
        String detail = "One or more fields are invalid. Please check the field(s) below: ";

        Problem problem = createProblemBuilder(HttpStatus.valueOf(statusCode.value()), problemType, detail)
                .userMessage(detail)
                .detail(String.join(", ", invalidFields))
                .build();

        return handleExceptionInternal(ex, problem, headers, statusCode, request);
    }

    private ResponseEntity<Object> handleNotFoundException(NoHandlerFoundException ex, HttpHeaders headers,
                                                           HttpStatusCode statusCode, WebRequest request) {

        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;

        String detail = String.format("The resource %s that you attempted to access does not exist",
                ex.getRequestURL());

        Problem problem = createProblemBuilder(HttpStatus.valueOf(statusCode.value()), problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, statusCode, request);
    }

    private ResponseEntity<Object> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex, HttpHeaders headers,
                                                                    HttpStatus status, WebRequest request) {

        ProblemType problemType = ProblemType.INVALID_PARAMETER;

        String detail = String.format("The URL parameter '%s' received the value '%s', which is of an invalid type. " +
                        "Please correct it and provide a value compatible with the type '%s'.", ex.getName(), ex.getValue(),
                ex.getRequiredType().getSimpleName());

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, headers, status, request);
    }

    private String joinPath(List<JsonMappingException.Reference> references) {
        return references.stream()
                .map(ref -> ref.getFieldName())
                .collect(Collectors.joining("."));
    }

    private static String buildDetailOfInvalidExceptionWithFieldsName(InvalidFormatException ex, String path) {
        String detail = String.format("The property %s received the value %s, which is of an invalid type. " +
                        "Please correct it and provide a value compatible with the type %s.",
                path, ex.getValue(), ex.getTargetType().getSimpleName());

        return detail;
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ProblemType problemType = ProblemType.RESOURCE_NOT_FOUND;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(EntityInUseException.class)
    public ResponseEntity<?> handleEntidadeEmUsoExpcetion(BusinessException ex, WebRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        ProblemType problemType = ProblemType.ENTITY_IN_USE;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ProblemType problemType = ProblemType.BUSINESS_EXCEPTION;
        String detail = ex.getMessage();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
                                                             HttpStatusCode statusCode, WebRequest request) {
        if (body == null) {
            body = Problem.builder()
                    .title(statusCode.toString())
                    .status(statusCode.value())
                    .build();
        } else if (body instanceof String) {
            body = Problem.builder()
                    .title(String.valueOf(body))
                    .status(statusCode.value())
                    .build();

        }
        return super.handleExceptionInternal(ex, body, headers, statusCode, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleUncaught(Exception ex, WebRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ProblemType problemType = ProblemType.SYSTEM_ERROR;
        String detail = GENERIC_ERROR_MESSAGE_USER;

        ex.printStackTrace();

        Problem problem = createProblemBuilder(status, problemType, detail).build();

        return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
    }

    private Problem.ProblemBuilder createProblemBuilder(HttpStatus status, ProblemType problemType, String detail) {
        return Problem.builder()
                .status(status.value())
                .type(problemType.getUri())
                .title(problemType.getTitle())
                .timestamp(LocalDateTime.now())
                .detail(detail);
    }
}