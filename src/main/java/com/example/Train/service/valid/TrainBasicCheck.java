package com.example.Train.service.valid;

import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.err.CheckException;
import com.example.Train.model.StopRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.Stop;
import com.example.Train.model.entity.Train;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class TrainBasicCheck {
    @Autowired
    TrainRepo trainRepo;

    @Autowired
    StopRepo stopRepo;

    public Train trainNoFindCheck(int trainNo) throws CheckException {
        Optional<Train> train = trainRepo.findByTrainNo(trainNo);
        if (train.isEmpty()) {
            throw new CheckException(List.of(new CheckErrors("trainNoNotExists", "Train No does not exists")));
        }
        return train.get();
    }

    public List<Stop> stopExistCheck(String via) throws CheckException {
        List<Stop> stopList = stopRepo.findByName(via);
        if (stopList.isEmpty()) {
            throw new CheckException(List.of(new CheckErrors("viaNotExists", "停靠站不存在")));
        }
        return stopList;
    }
}
