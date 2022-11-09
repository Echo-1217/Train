package com.example.Train.service.orther;

import com.example.Train.controller.dto.response.StopDetail;
import com.example.Train.controller.dto.response.TrainDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.err.CheckException;
import com.example.Train.model.TrainRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class ResponseHandler {
    @Autowired
    TrainRepo trainRepo;

    public TrainResponse getTrainResponse(int trainNo) {
        List<Map<String, ?>> dataList = trainRepo.findDataByTrainNo(trainNo);
        DateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        TrainResponse response = new TrainResponse();
        List<StopDetail> stopDetails = new ArrayList<>();
        response.setTrain_no(trainNo);
        response.setTrain_kind(NameKindTranslator.getName(dataList.get(0).get("TRAIN_KIND").toString()));
        dataList.forEach(map -> stopDetails.add(new StopDetail(map.get("name").toString(), simpleDateFormat.format(map.get("time")))));

        response.setStopDetails(stopDetails);

        return response;
    }

    public List<TrainDetail> getStationDetailList(String via) throws CheckException {
        List<TrainDetail> details = new ArrayList<>();
        List<Map<String, ?>> dataList = trainRepo.findByVia(via.trim());
        if (dataList.isEmpty()) {
            throw new CheckException(List.of(new CheckErrors("viaNotExists", "停靠站不存在")));
        }
        dataList.forEach(m -> details.add(new TrainDetail((int) m.get("train_no"), NameKindTranslator.getName(m.get("train_kind").toString()))));
        return details;
    }


}
