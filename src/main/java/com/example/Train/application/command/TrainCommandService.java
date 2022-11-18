package com.example.Train.application.command;

import com.example.Train.config.event.exception.customerErrorMsg.CustomizedException;
import com.example.Train.domain.aggregate.domainService.TrainDomainService;
import com.example.Train.domain.aggregate.entity.Train;
import com.example.Train.domain.command.AddTrainCommand;
import com.example.Train.infrastructure.outbound.TrainOutBoundService;
import com.example.Train.infrastructure.repo.TrainRepo;
import com.example.Train.intfa.dto.response.UniqueIdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TrainCommandService {
    @Autowired
    TrainRepo trainRepo;
    //    @Autowired
//    StopRepo stopRepo;
    @Autowired
    TrainDomainService trainDomainService;
    @Autowired
    TrainOutBoundService trainOutBoundService;

    @Transactional
    public UniqueIdResponse createTrainStops(AddTrainCommand command) throws CustomizedException {

//        AddTrainCommand command = new ObjectMapper().convertValue(connand, AddTrainCommand.class);

        //============= entity check ==============
//        CheckErrors checkKind = new Train().checkKind(command);
//        CheckErrors checkTime = new Stop().checkTime(command);
//        CheckErrors checkPlace = new Stop().placeCheck(command);
        //============= service check =============
        trainDomainService.summaryCheck(command);
        trainOutBoundService.trainApiCheck(command);
        //============ create entity ============

        Train train = new Train(command);
//        List<Stop> stopList = new Stop().buildList(command, train);

        // save to database
        trainRepo.save(train);
//        stopRepo.saveAll(stopList);
        return new UniqueIdResponse(train.getId());
    }
}
