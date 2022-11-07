package com.example.Train.service.command;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.response.UUIdResponse;
import com.example.Train.model.StopRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.Stop;
import com.example.Train.model.entity.Train;
import com.example.Train.model.exception.CheckException;
import com.example.Train.service.orther.MapTransfer;
import com.example.Train.service.orther.UUidCreator;
import com.example.Train.service.authentication.TrainCreateAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TrainCommandService {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    StopRepo stopRepo;
    @Autowired
    TrainCreateAuth trainCreateAuth;
    @Autowired
    MapTransfer mapTransfer;

    @Transactional
    public UUIdResponse createTrainStops(CreateTrainRequest request) throws CheckException {
        trainCreateAuth.trainCreatedCheck(request);

        Train train = new Train();

        train.setId(new UUidCreator().getTrainUUID());
        train.setTrainNo(Integer.parseInt(request.getTrain_no()));
        train.setTrainKind(mapTransfer.kindTransfer(request.getTrain_kind()));

        //============================================================================
        trainRepo.save(train);

        AtomicInteger seq = new AtomicInteger(1);


        request.getStops().forEach(stringMap ->
                stopRepo.save(new Stop(
                        new UUidCreator().getTrainUUID(),
                        train.getId(),
                        seq.getAndIncrement(),
                        stringMap.get("stop_name"),
                        LocalTime.parse(stringMap.get("stop_time")),
                        "N")));

        return new UUIdResponse(train.getId());
    }

}
