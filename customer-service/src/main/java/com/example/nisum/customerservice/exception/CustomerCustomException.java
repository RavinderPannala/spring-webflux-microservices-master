package com.example.nisum.customerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.time.LocalDate;

@RestControllerAdvice
public class CustomerCustomException {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorMessage> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ErrorMessage errorMessage = ErrorMessage.builder().errorDate(LocalDate.now()).errorDescription(ex.getMessage()).errorMessage(ex.getMessage()).errorStatusCode(HttpStatus.NOT_FOUND.value()).build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WebClientResponseException.class)
    public ResponseEntity<ErrorMessage>handleWebClientResponseException(WebClientResponseException ex){
        ErrorMessage errorMessage = ErrorMessage.builder().errorDate(LocalDate.now()).errorDescription(ex.getResponseBodyAsString()).errorMessage(ex.getMessage()).errorStatusCode(ex.getStatusCode().value()).build();
        return new ResponseEntity<>(errorMessage, ex.getStatusCode());
    }
}
