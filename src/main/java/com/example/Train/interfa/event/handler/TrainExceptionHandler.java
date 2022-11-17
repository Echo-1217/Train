package com.example.Train.interfa.event;

import com.example.Train.exception.err.CustomizedException;
import com.example.Train.exception.err.TrainParameterException;
import com.example.Train.exception.response.CheckErrorResponse;
import com.example.Train.exception.response.FieldErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;

@RestControllerAdvice
public class TrainExceptionHandler {

    //  最後一道防線
    @ExceptionHandler(Exception.class)
    public ResponseEntity<FieldErrorResponse> handler(Exception e) {
        FieldErrorResponse error = new FieldErrorResponse(e);
        e.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.valueOf(404));
    }

    // 捕捉 MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<FieldErrorResponse> handler(MethodArgumentNotValidException e) {
        FieldErrorResponse error = new FieldErrorResponse(e);
        e.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.valueOf(400));
    }

    // 捕捉 ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<FieldErrorResponse> handler(ConstraintViolationException e) {
        FieldErrorResponse error = new FieldErrorResponse(e);
        e.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.valueOf(400));
    }

    // 捕捉 MethodArgumentTypeMismatchException
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<FieldErrorResponse> handler(MethodArgumentTypeMismatchException e) {
        FieldErrorResponse error = new FieldErrorResponse(e);
        e.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TrainParameterException.class)
    public ResponseEntity<FieldErrorResponse> handler(TrainParameterException e) {
        FieldErrorResponse error = new FieldErrorResponse(e);
        e.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.valueOf(400));
    }

    @ExceptionHandler(CustomizedException.class)
    public ResponseEntity<CheckErrorResponse> handler(CustomizedException e) {
        CheckErrorResponse error = new CheckErrorResponse(e);
        e.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.valueOf(400));
    }

    @ExceptionHandler(DateTimeParseException.class)
    public ResponseEntity<CheckErrorResponse> handler(DateTimeParseException e) {
        CheckErrorResponse error = new CheckErrorResponse(e);
        e.printStackTrace();
        return new ResponseEntity<>(error, HttpStatus.valueOf(400));
    }
}