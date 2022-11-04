package com.example.Train.controller;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.request.TicketRequest;
import com.example.Train.controller.dto.response.StationDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.controller.dto.response.UUIdResponse;
import com.example.Train.model.exception.CheckException;
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
@Slf4j
public class TrainController {

    @Autowired
    TrainService service;

    @GetMapping("train/{trainNo}/stops")
    public TrainResponse getTargetTrainResponse(@PathVariable @Min(value = 1, message = "車次必須為正整數") int trainNo) throws Exception {
        return service.getTargetTrainResponse(trainNo);
    }

    @GetMapping("train")
    public List<StationDetail> getTrainByStop(@RequestParam @NotBlank(message = "Required String parameter 'via' is not present") String via) throws CheckException {
        return service.getStation(via);
    }

    @PostMapping("train")
    public UUIdResponse create(@Valid @RequestBody CreateTrainRequest request) throws Exception {
        return service.create(request);
    }

    @PostMapping("ticket")
    public UUIdResponse ticketCreate(@Valid @RequestBody TicketRequest request) throws CheckException {
        return service.getTicket(request);
    }
}
