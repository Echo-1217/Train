package com.example.Train.service.impl.command;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.response.UniqueIdResponse;
import com.example.Train.exception.err.CheckException;
import com.example.Train.model.StopRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.Train;
import com.example.Train.service.infs.TrainCommandService;
import com.example.Train.service.modifier.StopModifier;
import com.example.Train.service.modifier.TrainModifier;
import com.example.Train.service.valid.TrainCreateCheck;
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
    TrainCreateCheck trainCreateCheck;

    @Autowired
    StopModifier stopModifier;

    @Override
    @Transactional
    public UniqueIdResponse createTrainStops(CreateTrainRequest request) throws CheckException {
        //  set train
        trainCreateCheck.trainCreatedCheck(request);
        Train train = trainModifier.setAndGetTrain(Integer.parseInt(request.getTrainNo()), request.getTrainKind());
        trainRepo.save(train);
        //===================================================
        stopRepo.saveAll(stopModifier.setAndGetStopList(request.getStops(), train.getId()));

        return new UniqueIdResponse(train.getId());
    }

}
