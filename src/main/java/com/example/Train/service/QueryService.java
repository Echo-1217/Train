package com.example.Train.service;

import com.example.Train.controller.dto.response.StationDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.exception.err.CheckException;

import java.util.List;

public interface QueryService {
    TrainResponse getTrainStopsByTrainNO(int trainNo) throws CheckException;

    List<StationDetail> getTrainStopsByVia(String via) throws CheckException;
}
