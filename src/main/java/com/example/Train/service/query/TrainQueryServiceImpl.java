package com.example.Train.service.query;

import com.example.Train.controller.dto.response.StationDetail;
import com.example.Train.controller.dto.response.TrainDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.exception.err.CheckException;
import com.example.Train.model.StopRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.Stop;
import com.example.Train.model.entity.Train;
import com.example.Train.service.QueryService;
import com.example.Train.service.orther.MapTransfer;
import com.example.Train.service.valid.TrainCreateCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service()
public class TrainQueryServiceImpl implements QueryService {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    StopRepo stopRepo;
    @Autowired
    TrainCreateCheck trainCreateAuth;
    @Autowired
    MapTransfer mapTransfer;

    @Override
    public TrainResponse getTrainStopsByTrainNO(int trainNo) throws CheckException {
        Train train = trainCreateAuth.trainNoFindCheck(trainNo);

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

    @Override
    public List<StationDetail> getTrainStopsByVia(String via) throws CheckException {
        List<Stop> stopList = trainCreateAuth.stopExistCheck(via);

        List<StationDetail> details = new ArrayList<>();
        List<Train> trainList = new ArrayList<>();

        stopList.forEach(stop -> trainList.add(trainRepo.findById(stop.getTrainId()).get()));

        trainList.forEach(train -> details.add(new StationDetail(train.getTrainNo(), mapTransfer.kindTransfer(train.getTrainKind()))));

        return details;
    }
}
