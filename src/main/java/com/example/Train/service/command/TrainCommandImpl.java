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
//          An int value that may be updated atomically.
//          See the VarHandle specification for descriptions of the properties of atomic accesses.
//          An AtomicInteger is used in applications such as atomically incremented counters,
//          and cannot be used as a replacement for an Integer.
//          However, this class does extend Number to allow uniform access by tools and utilities that deal with numerically-based classes.
        AtomicInteger seq = new AtomicInteger(1);


        request.getTrainStops().forEach(via ->
                stopRepo.save(new Stop(
                        new UniqueIdCreator().getTrainUid(),
                        train.getId(),
                        seq.getAndIncrement(),
                        via.getStopName(),
                        LocalTime.parse(via.getStopTime()),
                        "N")));

        return new UniqueIdResponse(train.getId());
    }

}
