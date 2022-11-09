package com.example.Train.controller;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.request.TicketRequest;
import com.example.Train.controller.dto.response.TrainDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.controller.dto.response.UniqueIdResponse;
import com.example.Train.exception.err.CheckException;
import com.example.Train.service.impl.command.TicketCommandImpl;
import com.example.Train.service.impl.command.TrainCommandImpl;
import com.example.Train.service.infs.QueryService;
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
    QueryService trainQueryService;

    @Autowired
    TrainCommandImpl trainCommandService;

    @Autowired
    TicketCommandImpl ticketCommandService;


    @GetMapping("train/{trainNo}/stops")
    public TrainResponse getTargetTrainResponse(@PathVariable @Min(value = 1, message = "車次必須為正整數") int trainNo) throws CheckException {
        return trainQueryService.getTrainStopsByTrainNO(trainNo);
    }

    @GetMapping("train")
    public List<TrainDetail> getTrainByVia(@RequestParam @NotBlank(message = "Required String parameter 'via' is not present") String via) throws CheckException {
        return trainQueryService.getTrainStopsByVia(via);
    }

    @PostMapping("train")
    public UniqueIdResponse create(@Valid @RequestBody CreateTrainRequest request) throws Exception {
        return trainCommandService.createTrainStops(request);
    }

    @PostMapping("ticket")
    public UniqueIdResponse ticketCreate(@Valid @RequestBody TicketRequest request) throws CheckException {
        return ticketCommandService.createTicket(request);
    }
}
