package com.example.Train.service.appLayer.query;

import com.example.Train.controller.dto.response.TrainDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.exception.err.CustomizedException;
import com.example.Train.service.domainLayer.orther.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QueryService {

    @Autowired
    ResponseHandler responseHandler;

    public TrainResponse getTrainStopsByTrainNO(int trainNo) throws Exception {
        return responseHandler.buildTrainResponse(trainNo);
    }

    public List<TrainDetail> getTrainStopsByVia(String via) throws CustomizedException {
        return responseHandler.getStationDetailList(via);
    }
}
