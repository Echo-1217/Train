package com.example.Train.controller;

import com.example.Train.controller.dto.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class exceptionHandler {

    //  最後一道防線
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handler(Exception e) {

        ErrorResponse error = new ErrorResponse(e);
        e.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // 捕捉 MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handler(MethodArgumentNotValidException e) {
        ErrorResponse error = new ErrorResponse(e);
        e.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    // 捕捉 ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handler(ConstraintViolationException e) {

        ErrorResponse error = new ErrorResponse(e);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    // 捕捉 MethodArgumentTypeMismatchException
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handler(MethodArgumentTypeMismatchException e) {
        ErrorResponse error = new ErrorResponse(e);
        e.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

}