package com.malak.todolist.controllers;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex, WebRequest req) {
        return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), req.getDescription(false));
    }

    // 400 - Illegal args
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex, WebRequest req) {
        return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), req.getDescription(false));
    }

    // 400 - Malformed JSON
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleUnreadable(HttpMessageNotReadableException ex, WebRequest req) {
        return buildError(HttpStatus.BAD_REQUEST, "Malformed JSON request or missing body", req.getDescription(false));
    }

    // 400 - Validation on @RequestBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationErrors(MethodArgumentNotValidException ex, WebRequest req) {
        Map<String, String> fieldErrors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                err -> err.getField(),
                err -> err.getDefaultMessage(),
                (a, b) -> a
            ));

        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Validation Failed",
            "Invalid input data",
            req.getDescription(false),
            fieldErrors
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 400 - Validation on @PathVariable/@RequestParam
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleParamValidation(ConstraintViolationException ex, WebRequest req) {
        Map<String, String> violations = ex.getConstraintViolations().stream()
            .collect(Collectors.toMap(
                v -> v.getPropertyPath().toString(),
                v -> v.getMessage(),
                (a, b) -> a
            ));

        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Constraint Violation",
            "Invalid request parameters",
            req.getDescription(false),
            violations
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 500 - General error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneral(Exception ex, WebRequest req) {
        ex.printStackTrace(); // or use logger
        return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected server error", req.getDescription(false));
    }

    // Helper method
    private ResponseEntity<ErrorResponse> buildError(HttpStatus status, String message, String path) {
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            status.value(),
            status.getReasonPhrase(),
            message,
            path,
            null
        );
        return ResponseEntity.status(status).body(error);
    }

    // Standardized error model
    public static class ErrorResponse {
        private LocalDateTime timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
        private Map<String, String> details;

        public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path, Map<String, String> details) {
            this.timestamp = timestamp;
            this.status = status;
            this.error = error;
            this.message = message;
            this.path = path;
            this.details = details;
        }

        // getters and setters
        public LocalDateTime getTimestamp() { return timestamp; }
        public int getStatus() { return status; }
        public String getError() { return error; }
        public String getMessage() { return message; }
        public String getPath() { return path; }
        public Map<String, String> getDetails() { return details; }

        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
        public void setStatus(int status) { this.status = status; }
        public void setError(String error) { this.error = error; }
        public void setMessage(String message) { this.message = message; }
        public void setPath(String path) { this.path = path; }
        public void setDetails(Map<String, String> details) { this.details = details; }
    }
}
