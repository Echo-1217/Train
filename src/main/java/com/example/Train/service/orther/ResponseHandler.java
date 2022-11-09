package com.example.Train.service.orther;

import com.example.Train.controller.dto.response.StopDetail;
import com.example.Train.controller.dto.response.TrainDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.err.CheckException;
import com.example.Train.model.StopRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.Stop;
import com.example.Train.model.entity.Train;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ResponseHandler {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    StopRepo stopRepo;

    public TrainResponse getTrainResponse(Train train) {

        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
        TrainResponse response = new TrainResponse();
        List<StopDetail> stopDetails = new ArrayList<>();
        List<Stop> stopList = stopRepo.findByTrainId(train.getId());

        response.setTrain_no(train.getTrainNo());
        response.setTrain_kind(NameKindTranslator.getName(train.getTrainKind()));
        stopList.forEach(stop -> stopDetails.add(new StopDetail(stop.getName(), stop.getTime().format(df))));
        response.setStopDetails(stopDetails);

        return response;
    }

    public List<TrainDetail> getStationDetailList(String via) throws CheckException {
        List<TrainDetail> details = new ArrayList<>();
        List<Map<String, ?>> mapList = trainRepo.findByVia(via.trim());
        if (mapList.isEmpty()) {
            throw new CheckException(List.of(new CheckErrors("viaNotExists", "停靠站不存在")));
        }
        mapList.forEach(m -> details.add(new TrainDetail((int) m.get("train_no"), NameKindTranslator.getName(m.get("train_kind").toString()))));
        return details;
    }


}
