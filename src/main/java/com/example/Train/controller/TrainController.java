package com.example.Train.controller;

import com.example.Train.controller.dto.response.ErrorResponse;
import com.example.Train.controller.dto.response.GetTargetTrainResponse;
import com.example.Train.service.TrainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Min;
import java.util.HashSet;

@Validated
@RestController
@RequestMapping(path = "train")
@Slf4j
public class TrainController {

    @Autowired
    TrainService service;

    @GetMapping("/{trainNo}/stops")
    public GetTargetTrainResponse getTargetTrainResponse(@PathVariable @Min(value = 1, message = "車次必須為正整數") int trainNo) throws Exception {
        return service.getTargetTrainResponse(trainNo);
    }
}
