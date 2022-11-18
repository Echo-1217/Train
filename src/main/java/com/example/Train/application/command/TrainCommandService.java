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
    @Autowired
    TrainDomainService trainDomainService;
    @Autowired
    TrainOutBoundService trainOutBoundService;

    @Transactional
    public UniqueIdResponse createTrainStops(AddTrainCommand command) throws CustomizedException {
        //============= service check =============
        trainDomainService.summaryCheck(command);
        trainOutBoundService.trainApiCheck(command);
        //============ create entity ============
        Train train = new Train(command);
        // save to database
        trainRepo.save(train);
        return new UniqueIdResponse(train.getId());
    }
}
