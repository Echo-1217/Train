package com.example.Train.service.command;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.response.UniqueIdResponse;
import com.example.Train.model.StopRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.Stop;
import com.example.Train.model.entity.Train;
import com.example.Train.exception.err.CheckException;
import com.example.Train.service.orther.MapTransfer;
import com.example.Train.service.orther.UniqueIdCreator;
import com.example.Train.service.valid.TrainCreateCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class TrainCommandImpl implements com.example.Train.service.TrainCommandService {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    StopRepo stopRepo;
    @Autowired
    TrainCreateCheck trainCreateAuth;
    @Autowired
    MapTransfer mapTransfer;



    @Override
    @Transactional
    public UniqueIdResponse createTrainStops(CreateTrainRequest request) throws CheckException {
        trainCreateAuth.trainCreatedCheck(request);

        Train train = new Train();

        train.setId(new UniqueIdCreator().getTrainUid());
        train.setTrainNo(Integer.parseInt(request.getTrainNo()));
        train.setTrainKind(mapTransfer.kindTransfer(request.getTrainKind()));

        //============================================================================
        trainRepo.save(train);

        AtomicInteger seq = new AtomicInteger(1);


        request.getStops().forEach(stringMap ->
                stopRepo.save(new Stop(
                        new UniqueIdCreator().getTrainUid(),
                        train.getId(),
                        seq.getAndIncrement(),
                        stringMap.get("stop_name"),
                        LocalTime.parse(stringMap.get("stop_time")),
                        "N")));

        return new UniqueIdResponse(train.getId());
    }

}
