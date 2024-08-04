package br.com.fourcamp.smc.SMC.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for managing exceptions across the entire application.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles CustomException instances.
     *
     * @param e the custom exception
     * @return a ResponseEntity containing the error message and status code
     */
    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e.getMessage());
    }

    /**
     * Handles generic Exception instances.
     *
     * @param e the exception
     * @return a ResponseEntity containing a generic error message and a 500 status code
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred: " + e.getMessage());
    }
}