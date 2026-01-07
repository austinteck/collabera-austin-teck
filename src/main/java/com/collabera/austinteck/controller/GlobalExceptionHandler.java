package com.collabera.austinteck.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Handler for returning meaningful exception messages
 * @author Austin Teck
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Build meaningful String exception message upon MethodArgumentNotValidException thrown
     * @param ex Exception thrown
     * @return HTTP 400, exception message
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException ex){
        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(err ->
                errors.append(err.getField())
                        .append(": ")
                        .append(err.getDefaultMessage())
                        .append(";"));

        return ResponseEntity.badRequest().body(errors.toString());
    }

    /**
     * Build meaningful String exception message upon IllegalArgumentException thrown
     * @param ex Exception thrown
     * @return HTTP 400, exception message
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}
