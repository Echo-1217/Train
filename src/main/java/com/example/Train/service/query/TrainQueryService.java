package com.example.Train.service.query;

import com.example.Train.controller.dto.response.StationDetail;
import com.example.Train.controller.dto.response.TrainDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.model.StopRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.Stop;
import com.example.Train.model.entity.Train;
import com.example.Train.model.exception.CheckException;
import com.example.Train.service.orther.MapTransfer;
import com.example.Train.service.authentication.TrainBasicAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainQueryService {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    StopRepo stopRepo;
    @Autowired
    TrainBasicAuth basicAuth;
    @Autowired
    MapTransfer mapTransfer;

    public TrainResponse getTrainStopsByTrainNO(int trainNo) throws CheckException {
        Train train = basicAuth.trainNoFindCheck(trainNo);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
        TrainResponse response = new TrainResponse();
        List<TrainDetail> trainDetails = new ArrayList<>();
        List<Stop> stopList = stopRepo.findByTrainId(train.getId());

        response.setTrain_no(trainNo);
        response.setTrain_kind(mapTransfer.kindTransfer(train.getTrainKind()));
        stopList.forEach(stop -> trainDetails.add(new TrainDetail(stop.getName(), stop.getTime().format(df))));
        response.setTrainDetails(trainDetails);

        return response;
    }

    public List<StationDetail> getTrainStopsByVia(String via) throws CheckException {
        List<Stop> stopList = basicAuth.stopExistCheck(via);

        List<StationDetail> details = new ArrayList<>();
        List<Train> trainList = new ArrayList<>();

        stopList.forEach(stop -> {
            if (trainRepo.findById(stop.getTrainId()).isPresent()) {
                trainList.add(trainRepo.findById(stop.getTrainId()).get());
            }
        });

        trainList.forEach(train -> details.add(new StationDetail(train.getTrainNo(), mapTransfer.kindTransfer(train.getTrainKind()))));

        return details;
    }
}
