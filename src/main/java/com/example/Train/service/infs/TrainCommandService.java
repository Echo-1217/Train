package com.example.Train.service.infs;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.response.UniqueIdResponse;
import com.example.Train.exception.err.CustomizedException;

import javax.transaction.Transactional;

public interface TrainCommandService {
    UniqueIdResponse createTrainStops(CreateTrainRequest request) throws CustomizedException;
}
