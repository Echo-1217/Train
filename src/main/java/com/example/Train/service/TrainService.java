package com.example.Train.service;

import com.example.Train.controller.dto.request.CreateRequest;
import com.example.Train.controller.dto.response.CreateResponse;
import com.example.Train.controller.dto.response.StationDetail;
import com.example.Train.controller.dto.response.TrainDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.model.StopRepo;
import com.example.Train.model.TicketRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.TrainErrorException;
import com.example.Train.model.entity.Stop;
import com.example.Train.model.entity.Train;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class TrainService {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    StopRepo stopRepo;
    @Autowired
    TicketRepo ticketRepo;
    @Autowired
    Authentication authentication;

    public TrainResponse getTargetTrainResponse(int trainNo) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
        TrainResponse response = new TrainResponse();
        List<TrainDetail> trainDetails = new ArrayList<>();
        Optional<Train> train = trainRepo.findByTrainNo(trainNo);
        Map<String, String> map = Map.of("A", "諾亞方舟號", "B", "霍格華茲號");

        if (train.isPresent()) {
            response.setTrain_no(trainNo);
            response.setTrain_kind(map.get(train.get().getTrainKind()));
            List<Stop> stopList = stopRepo.findByTrainId(train.get().getId());
            stopList.forEach(stop -> trainDetails.add(new TrainDetail(stop.getName(), stop.getTime().format(df))));
            response.setTrainDetails(trainDetails);
        } else {
            throw new TrainErrorException("車次不存在", "trainNo", "404");
        }

        return response;
    }

    public List<StationDetail> getStation(String via) {
        List<StationDetail> details = new ArrayList<>();
        Map<String, String> map = Map.of("A", "諾亞方舟號", "B", "霍格華茲號");
        List<Stop> stopList = stopRepo.findByName(via.trim());
        if (stopList.isEmpty()) {
            throw new TrainErrorException("車站不存在", "via", "404");
        }
        List<Train> trainList = new ArrayList<>();
        stopList.forEach(stop -> {
            if (trainRepo.findById(stop.getTrainId()).isPresent()) {
                trainList.add(trainRepo.findById(stop.getTrainId()).get());
            }
        });
        trainList.forEach(train -> details.add(new StationDetail(train.getTrainNo(), map.get(train.getTrainKind()))));
        return details;
    }

    @Transactional
    public CreateResponse create(CreateRequest request) throws Exception {
        if (!authentication.timeChecked(request)) {
            throw new Exception("Train Stops is not sorted");
        }
        authentication.apiCheck(Integer.parseInt(request.getTrain_no()));

        Map<String, String> map = Map.of("諾亞方舟號", "A", "霍格華茲號", "B");
        Train train = new Train();
        String uid = java.util.UUID.randomUUID().toString().replace("-", "").toUpperCase();

        train.setId(uid);

        train.setTrainNo(Integer.parseInt(request.getTrain_no()));
        train.setTrainKind(map.get(request.getTrain_kind()));

        trainRepo.save(train);

        AtomicInteger seq = new AtomicInteger(1);


        request.getStops().forEach(stringMap ->
                stopRepo.save(new Stop(
                        java.util.UUID.randomUUID().toString().replace("-", "").toUpperCase(),
                        train.getId(),
                        seq.getAndIncrement(),
                        stringMap.get("stop_name"),
                        LocalTime.parse(stringMap.get("stop_time")),
                        "N")));

        return new CreateResponse(train.getId());
    }


}
