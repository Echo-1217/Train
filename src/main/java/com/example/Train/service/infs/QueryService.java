package com.example.Train.service.infs;

import com.example.Train.controller.dto.response.TrainDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.exception.err.CheckException;

import java.util.List;

public interface QueryService {
    TrainResponse getTrainStopsByTrainNO(int trainNo) throws CheckException;

    List<TrainDetail> getTrainStopsByVia(String via) throws CheckException;
}
