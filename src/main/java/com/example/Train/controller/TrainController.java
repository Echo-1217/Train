package com.example.Train.controller;

import com.example.Train.controller.dto.request.CreateRequest;
import com.example.Train.controller.dto.response.CreateResponse;
import com.example.Train.controller.dto.response.StationDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.service.TrainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Validated
@RestController
@RequestMapping(path = "train")
@Slf4j
public class TrainController {

    @Autowired
    TrainService service;

    @GetMapping("/{trainNo}/stops")
    public TrainResponse getTargetTrainResponse(@PathVariable @Min(value = 1, message = "車次必須為正整數") int trainNo) {
        return service.getTargetTrainResponse(trainNo);
    }

    @GetMapping()
    public List<StationDetail> getTrainByStop(@RequestParam @NotBlank(message = "Required String parameter 'via' is not present") String via) {
        return service.getStation(via);
    }

    @PostMapping()
    public CreateResponse create(@Valid @RequestBody CreateRequest request) throws Exception {
        return  service.create(request);
    }
}
