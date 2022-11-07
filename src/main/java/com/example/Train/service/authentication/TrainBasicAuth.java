package com.example.Train.service.authentication;

import com.example.Train.model.StopRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.Stop;
import com.example.Train.model.entity.Train;
import com.example.Train.model.exception.CheckErrors;
import com.example.Train.model.exception.CheckException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class TrainBasicAuth {
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
