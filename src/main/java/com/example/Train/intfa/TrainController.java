package com.example.Train.intfa;

import com.example.Train.application.command.TicketCommandService;
import com.example.Train.application.command.TrainCommandService;
import com.example.Train.application.query.QueryService;
import com.example.Train.config.event.exception.customerErrorMsg.CustomizedException;
import com.example.Train.domain.command.AddTicketCommand;
import com.example.Train.domain.command.AddTrainCommand;
import com.example.Train.domain.command.QueryStopCommand;
import com.example.Train.domain.command.QueryTrainCommand;
import com.example.Train.intfa.dto.request.CreateTrainRequest;
import com.example.Train.intfa.dto.request.TicketRequest;
import com.example.Train.intfa.dto.response.TrainDetail;
import com.example.Train.intfa.dto.response.TrainResponse;
import com.example.Train.intfa.dto.response.UniqueIdResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    QueryService trainQueryService;
    @Autowired
    TrainCommandService trainCommandService;
    @Autowired
    TicketCommandService ticketCommandService;

    @GetMapping("train/{trainNo}/stops")
    public TrainResponse getTargetTrainResponse(@PathVariable @Min(value = 0, message = "車次必須為正整數") int trainNo) throws Exception {
        QueryTrainCommand command = objectMapper.convertValue(trainNo, QueryTrainCommand.class);
        return trainQueryService.getTrainStopsByTrainNO(command);
    }

    @GetMapping("train")
    public List<TrainDetail> getTrainByVia(@RequestParam @NotBlank(message = "Required String parameter 'via' is not present") String via) throws CustomizedException {
        QueryStopCommand command = objectMapper.convertValue(via, QueryStopCommand.class);
        return trainQueryService.getTrainStopsByVia(command);
    }

    @PostMapping("train")
    public UniqueIdResponse create(@Valid @RequestBody CreateTrainRequest request) throws Exception {
        AddTrainCommand command = objectMapper.convertValue(request, AddTrainCommand.class);
        return trainCommandService.createTrainStops(command);
    }

    @PostMapping("ticket")
    public UniqueIdResponse ticketCreate(@Valid @RequestBody TicketRequest request) throws CustomizedException {
        AddTicketCommand command = objectMapper.convertValue(request, AddTicketCommand.class);
        return ticketCommandService.createTicket(command);
    }
}
