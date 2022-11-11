package com.example.Train.service.impl.command;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.response.UniqueIdResponse;
import com.example.Train.exception.err.CustomizedException;
import com.example.Train.model.StopRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.Train;
import com.example.Train.service.infs.TrainCommandService;
import com.example.Train.service.domain.modifier.StopModifier;
import com.example.Train.service.domain.modifier.TrainModifier;
import com.example.Train.service.domain.valid.TrainDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TrainCommandImpl implements TrainCommandService {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    StopRepo stopRepo;
    @Autowired
    TrainModifier trainModifier;
    @Autowired
    TrainDomainService trainDomainService;

    @Autowired
    StopModifier stopModifier;

    @Override
    @Transactional
    public UniqueIdResponse createTrainStops(CreateTrainRequest request) throws CustomizedException {
        //  set train
        trainDomainService.trainCreatedCheck(request);
        Train train = trainModifier.buildTrain(Integer.parseInt(request.getTrainNo()), request.getTrainKind());
        trainRepo.save(train);
        //==================
        stopRepo.saveAll(stopModifier.buildStopList(request.getStops(), train.getId()));

        return new UniqueIdResponse(train.getId());
    }

}
