package com.example.Train.application.command;

import com.example.Train.adapter.dto.request.CreateTrainRequest;
import com.example.Train.adapter.dto.response.UniqueIdResponse;
import com.example.Train.application.outBound.TrainOutBoundService;
import com.example.Train.exception.err.CheckErrors;
import com.example.Train.adapter.obj.AddTrain;
import com.example.Train.domain.entity.Stop;
import com.example.Train.domain.entity.Train;
import com.example.Train.adapter.repository.StopRepo;
import com.example.Train.adapter.repository.TrainRepo;
import com.example.Train.domain.domainService.TrainDomainService;
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

        AddTrain addTrain = new ObjectMapper().convertValue(request, AddTrain.class);

        //============= single field check ==============
        CheckErrors checkKind = new Train().checkKind(addTrain);
        CheckErrors checkTime = new Stop().checkTime(addTrain);
        CheckErrors checkPlace = new Stop().placeCheck(addTrain);
        //============= multi field check =============
        trainDomainService.summaryCheck(addTrain, checkKind, checkTime, checkPlace);
        trainOutBoundService.trainApiCheck(addTrain);
        //============ create entity ============

        Train train = new Train(addTrain);
        List<Stop> stopList = new Stop().buildList(addTrain, train);

        // save to database
        trainRepo.save(train);
        stopRepo.saveAll(stopList);
        return new UniqueIdResponse(train.getId());
    }
}
