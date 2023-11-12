package com.employee.management.handler;

import com.employee.management.exception.NotFoundException;
import com.employee.management.model.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> dataNotFound(NotFoundException ex){
        int status= HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status).body(ErrorResponse.builder()
                .status(status).message(ex.getMessage()).build());
    }

}