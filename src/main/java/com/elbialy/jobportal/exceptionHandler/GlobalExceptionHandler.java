package com.elbialy.jobportal.exceptionHandler;

import com.elbialy.jobportal.errors.ErrorResponse;
import com.elbialy.jobportal.exceptions.EmailAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse>handleValidationExceptions(MethodArgumentNotValidException ex){
        Map<String , String > errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        ErrorResponse errorResponse = new ErrorResponse("error","Validation failed",errors);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse>handleRunTimeExceptions(EmailAlreadyExistsException ex){
        ErrorResponse errorResponse = new ErrorResponse("error", ex.getMessage(), null);
        return new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);

    }
}
