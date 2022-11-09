package com.example.Train.service.impl.query;

import com.example.Train.controller.dto.response.TrainDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.exception.err.CheckException;
import com.example.Train.service.infs.QueryService;
import com.example.Train.service.orther.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainQueryServiceImpl implements QueryService {

    @Autowired
    ResponseHandler responseHandler;

    @Override
    public TrainResponse getTrainStopsByTrainNO(int trainNo) throws CheckException {
        return responseHandler.getTrainResponse(trainNo);
    }

    @Override
    public List<TrainDetail> getTrainStopsByVia(String via) throws CheckException {
        return responseHandler.getStationDetailList(via);
    }
}
