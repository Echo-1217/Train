package com.example.Train.service.infs;

import com.example.Train.controller.dto.response.TrainDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.exception.err.CustomizedException;

import java.util.List;

public interface QueryService {
    TrainResponse getTrainStopsByTrainNO(int trainNo) throws CustomizedException;

    List<TrainDetail> getTrainStopsByVia(String via) throws CustomizedException;
}
