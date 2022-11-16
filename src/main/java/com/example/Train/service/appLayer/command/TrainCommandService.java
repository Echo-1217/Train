package com.example.Train.service.appLayer.command;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.response.UniqueIdResponse;
import com.example.Train.exception.err.CheckErrors;
import com.example.Train.model.addObj.AddTrain;
import com.example.Train.model.entity.Stop;
import com.example.Train.model.entity.Train;
import com.example.Train.model.repository.StopRepo;
import com.example.Train.model.repository.TrainRepo;
import com.example.Train.service.domainLayer.data.TrainDomainService;
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

    @Transactional
    public UniqueIdResponse createTrainStops(CreateTrainRequest request) throws Exception {

        AddTrain addTrain = new ObjectMapper().convertValue(request, AddTrain.class);

        //============= single field check ==============
        CheckErrors checkKind = new Train().checkKind(addTrain);
        CheckErrors checkTime = new Stop().checkTime(addTrain);
        CheckErrors checkPlace = new Stop().placeCheck(addTrain);
        //============= multi field check =============

        trainDomainService.summaryCheck(addTrain, checkKind, checkTime, checkPlace);

        //============ create entity ============

        Train train = new Train(addTrain);
        List<Stop> stopList = new Stop().buildList(addTrain, train);

        // save to database
        trainRepo.save(train);
        stopRepo.saveAll(stopList);
        return new UniqueIdResponse(train.getId());
    }
}
