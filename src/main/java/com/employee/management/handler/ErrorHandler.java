package com.employee.management.handler;

import com.employee.management.exception.NotFoundException;
import com.employee.management.model.ErrorResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> dataNotFound(NotFoundException ex) {
        int status = HttpStatus.NOT_FOUND.value();
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .status(status)
                        .message(ex.getMessage()).build());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {

        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .status(status)
                        .message(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                        .build());
    }

    /*@ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(DuplicateKeyException ex) {

        int status = HttpStatus.BAD_REQUEST.value();
        return ResponseEntity.status(status)
                .body(ErrorResponse.builder()
                        .status(status)
                        .message(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage())
                        .build());
    }*/

}