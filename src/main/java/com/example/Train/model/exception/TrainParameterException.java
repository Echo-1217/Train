package com.example.Train.model.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TrainParameterException extends RuntimeException {

    private String code;
    private String message;
    private String fieldName;

}