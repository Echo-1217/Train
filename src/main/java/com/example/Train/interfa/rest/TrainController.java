package com.example.Train.interfa.rest;

import com.example.Train.interfa.rest.dto.request.CreateTrainRequest;
import com.example.Train.interfa.rest.dto.request.TicketRequest;
import com.example.Train.interfa.rest.dto.response.TrainDetail;
import com.example.Train.interfa.rest.dto.response.TrainResponse;
import com.example.Train.interfa.rest.dto.response.UniqueIdResponse;
import com.example.Train.interfa.event.exception.customerErrorMsg.CustomizedException;
import com.example.Train.application.command.TicketCommandService;
import com.example.Train.application.command.TrainCommandService;
import com.example.Train.application.query.QueryService;
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
    TrainCommandService trainCommandService;

    @Autowired
    TicketCommandService ticketCommandService;


    @GetMapping("train/{trainNo}/stops")
    public TrainResponse getTargetTrainResponse(@PathVariable @Min(value = 0, message = "車次必須為正整數") int trainNo) throws Exception {
        return trainQueryService.getTrainStopsByTrainNO(trainNo);
    }

    @GetMapping("train")
    public List<TrainDetail> getTrainByVia(@RequestParam @NotBlank(message = "Required String parameter 'via' is not present") String via) throws CustomizedException {
        return trainQueryService.getTrainStopsByVia(via);
    }

    @PostMapping("train")
    public UniqueIdResponse create(@Valid @RequestBody CreateTrainRequest request) throws Exception {
        return trainCommandService.createTrainStops(request);
    }

    @PostMapping("ticket")
    public UniqueIdResponse ticketCreate(@Valid @RequestBody TicketRequest request) throws CustomizedException {
        return ticketCommandService.createTicket(request);
    }
}
