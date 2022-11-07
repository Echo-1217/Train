package com.example.Train.service;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.response.UniqueIdResponse;
import com.example.Train.exception.err.CheckException;

public interface TrainCommandService {

    UniqueIdResponse createTrainStops(CreateTrainRequest request) throws CheckException;
}
