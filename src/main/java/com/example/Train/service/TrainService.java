package com.example.Train.service;

import com.example.Train.controller.dto.response.GetTargetTrainResponse;
import com.example.Train.controller.dto.response.Stop;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.TrainStopRepo;
import com.example.Train.model.TrainTicketRepo;
import com.example.Train.model.entity.Train;
import com.example.Train.model.entity.TrainStop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class TrainService {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    TrainStopRepo stopRepo;
    @Autowired
    TrainTicketRepo ticketRepo;

    public GetTargetTrainResponse getTargetTrainResponse(Integer trainNo) throws Exception {
        DateTimeFormatter df =DateTimeFormatter.ofPattern("HH:mm");
        GetTargetTrainResponse response=new GetTargetTrainResponse();
        List<Stop>stops=new ArrayList<>();
        Optional<Train> train=trainRepo.findByTrainNo(trainNo);
        Map<String,String> map = Map.of("A","諾亞方舟號","B","霍格華茲號");
        if(trainNo < 0){

        }
        if(train.isPresent()) {
            response.setTrain_no(trainNo);
            response.setTrain_kind(map.get(train.get().getTrainKind()));
            List<TrainStop> stopList = stopRepo.findByTrainUuid(train.get().getUuid());
            stopList.forEach(stop -> stops.add(new Stop(stop.getName(), stop.getTime().format(df))));
            response.setStops(stops);
        }
        else {
            throw new Exception("車次不存在");
        }

        return response;
    }
}
