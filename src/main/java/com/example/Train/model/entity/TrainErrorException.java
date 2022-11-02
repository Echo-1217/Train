package com.example.Train.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TrainErrorException extends RuntimeException {

    private String message;

    private String fieldName;

    private String code;
}
