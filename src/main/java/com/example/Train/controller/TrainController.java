package com.example.Train.controller;

import com.example.Train.controller.dto.response.ErrorResponse;
import com.example.Train.controller.dto.response.GetTargetTrainResponse;
import com.example.Train.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;

@Validated
@RestController
@RequestMapping(path = "train")
public class TrainController {

    @Autowired
    TrainService service;
    @GetMapping("/{trainNo}/stops")
    public GetTargetTrainResponse getTargetTrainResponse(@PathVariable @Min(value = 1,message = "車次必須為正整數") Integer trainNo) throws Exception {
        try {
            return service.getTargetTrainResponse(trainNo);
        }
        catch (Exception e){
            throw new Exception("Validate Failed");
        }
    }
}
