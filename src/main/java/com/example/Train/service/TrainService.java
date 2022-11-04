package com.example.Train.service;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.request.TicketRequest;
import com.example.Train.controller.dto.response.StationDetail;
import com.example.Train.controller.dto.response.TrainDetail;
import com.example.Train.controller.dto.response.TrainResponse;
import com.example.Train.controller.dto.response.UUIdResponse;
import com.example.Train.model.StopRepo;
import com.example.Train.model.TicketRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.Stop;
import com.example.Train.model.entity.Ticket;
import com.example.Train.model.entity.Train;
import com.example.Train.model.exception.CheckException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
    CalTicket calTicket;
    @Autowired
    Authentication authentication;
    @Autowired
    MapTransfer mapTransfer;

    public TrainResponse getTargetTrainResponse(int trainNo) throws Exception {
        Train train = authentication.trainNoFindCheck(trainNo);

        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");
        TrainResponse response = new TrainResponse();
        List<TrainDetail> trainDetails = new ArrayList<>();
        List<Stop> stopList = stopRepo.findByTrainId(train.getId());

        response.setTrain_no(trainNo);
        response.setTrain_kind(mapTransfer.kindTransfer(train.getTrainKind()));
        stopList.forEach(stop -> trainDetails.add(new TrainDetail(stop.getName(), stop.getTime().format(df))));
        response.setTrainDetails(trainDetails);

        return response;
    }

    public List<StationDetail> getStation(String via) throws CheckException {
        List<Stop> stopList = authentication.stopExistCheck(via);

        List<StationDetail> details = new ArrayList<>();
        List<Train> trainList = new ArrayList<>();

        stopList.forEach(stop -> {
            if (trainRepo.findById(stop.getTrainId()).isPresent()) {
                trainList.add(trainRepo.findById(stop.getTrainId()).get());
            }
        });

        trainList.forEach(train -> details.add(new StationDetail(train.getTrainNo(), mapTransfer.kindTransfer(train.getTrainKind()))));

        return details;
    }

    @Transactional
    public UUIdResponse create(CreateTrainRequest request) throws CheckException {
        authentication.trainCreatedCheck(request);
        authentication.trainApiCheck(Integer.parseInt(request.getTrain_no()));

        Train train = new Train();

        train.setId(java.util.UUID.randomUUID().toString().replace("-", "").toUpperCase());
        train.setTrainNo(Integer.parseInt(request.getTrain_no()));
        train.setTrainKind(mapTransfer.kindTransfer(request.getTrain_kind()));

        //============================================================================
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

        return new UUIdResponse(train.getId());
    }

    public UUIdResponse getTicket(TicketRequest request) throws CheckException {
        authentication.ticketCreatedCheck(request);
        authentication.dateValueCheck(request.getTake_date());
        Ticket ticket = new Ticket();
        ticket.setTickNo(java.util.UUID.randomUUID().toString().replace("-", "").toUpperCase());
        ticket.setTrainUuid(authentication.trainNoFindCheck(Integer.parseInt(request.getTrain_no())).getId());
        ticket.setPrice(calTicket.getTicketPrice());
        ticket.setFromStop(request.getFrom_stop());
        ticket.setToStop(request.getTo_stop());
        ticket.setTakeDate(request.getTake_date());
        ticketRepo.save(ticket);
        return new UUIdResponse(ticket.getTrainUuid());
    }
}
