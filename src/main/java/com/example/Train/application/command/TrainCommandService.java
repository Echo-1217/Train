package com.example.Train.application.command;

import com.example.Train.interfa.rest.dto.request.CreateTrainRequest;
import com.example.Train.interfa.rest.dto.response.UniqueIdResponse;
import com.example.Train.application.command.outBound.TrainOutBoundService;
import com.example.Train.interfa.event.exception.customerErrorMsg.CheckErrors;
import com.example.Train.domain.command.AddTrainCommand;
import com.example.Train.domain.aggregate.entity.Stop;
import com.example.Train.domain.aggregate.entity.Train;
import com.example.Train.infrastructure.StopRepo;
import com.example.Train.infrastructure.TrainRepo;
import com.example.Train.domain.aggregate.domainService.TrainDomainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TrainCommandService {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    StopRepo stopRepo;
    @Autowired
    TrainDomainService trainDomainService;
    @Autowired
    TrainOutBoundService trainOutBoundService;
    @Transactional
    public UniqueIdResponse createTrainStops(CreateTrainRequest request) throws Exception {

        AddTrainCommand command = new ObjectMapper().convertValue(request, AddTrainCommand.class);

        //============= entity check ==============
        CheckErrors checkKind = new Train().checkKind(command);
        CheckErrors checkTime = new Stop().checkTime(command);
        CheckErrors checkPlace = new Stop().placeCheck(command);
        //============= service check =============
        trainDomainService.summaryCheck(command, checkKind, checkTime, checkPlace);
        trainOutBoundService.trainApiCheck(command);
        //============ create entity ============

        Train train = new Train(command);
        List<Stop> stopList = new Stop().buildList(command, train);

        // save to database
        trainRepo.save(train);
        stopRepo.saveAll(stopList);
        return new UniqueIdResponse(train.getId());
    }
}
