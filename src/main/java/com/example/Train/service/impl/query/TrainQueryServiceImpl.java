package com.example.Train.service.impl.query;

import com.example.Train.controller.dto.response.TrainDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.exception.err.CheckException;
import com.example.Train.model.entity.Train;
import com.example.Train.service.infs.QueryService;
import com.example.Train.service.orther.ResponseHandler;
import com.example.Train.service.valid.TrainCreateCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainQueryServiceImpl implements QueryService {

    @Autowired
    ResponseHandler responseHandler;
    @Autowired
    TrainCreateCheck trainCreateCheck;

    @Override
    public TrainResponse getTrainStopsByTrainNO(int trainNo) throws CheckException {
        Train train = trainCreateCheck.trainNoFindCheck(trainNo);
        return responseHandler.getTrainResponse(train);
    }

    @Override
    public List<TrainDetail> getTrainStopsByVia(String via) throws CheckException {
        return responseHandler.getStationDetailList(via);
    }
}
