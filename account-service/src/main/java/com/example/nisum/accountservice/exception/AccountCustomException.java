package com.example.nisum.accountservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;

@RestControllerAdvice
public class AccountCustomException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder().errorDate(LocalDate.now()).errorDescription(ex.getMessage()).errorMessage(ex.getMessage()).errorStatusCode(HttpStatus.NOT_FOUND.value()).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> handleException(Exception ex) {
        ErrorMessage errorMessage = ErrorMessage.builder().errorDate(LocalDate.now()).errorDescription(ex.getMessage()).errorMessage(ex.getMessage()).errorStatusCode(HttpStatus.NOT_FOUND.value()).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
