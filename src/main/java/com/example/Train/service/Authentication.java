package com.example.Train.service;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.request.TicketRequest;
import com.example.Train.controller.dto.response.apiResult.CheckRes;
import com.example.Train.model.StopRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.*;
import com.example.Train.model.exception.CheckException;
import com.example.Train.model.exception.TrainParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
@Slf4j
public class Authentication {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    StopRepo stopRepo;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    MapTransfer mapTransfer;

    public void stopTimeChecked(CreateTrainRequest request) throws CheckException {
        if (!Objects.equals(request.getStops().stream().sorted(Comparator.comparing(t -> t.get("stop_time"),
                                Comparator.nullsLast(Comparator.naturalOrder())))//  將空元素被認為大於非空元素
                        .toList()
                , request.getStops())) {

            throw new CheckException(List.of(new CheckErrors("TrainStopsNotSorted", "Train Stops is not sorted")));

        }
        ;
    }

    public void trainCreateParamChecked(CreateTrainRequest request) throws CheckException {
        List<CheckErrors> checkErrorsList = new ArrayList<>();
        // wrong No
        if (trainRepo.findByTrainNo(Integer.parseInt(request.getTrain_no())).isPresent()) {
            checkErrorsList.add(new CheckErrors("TrainNoExists", "Train No is exists"));
        }
        //invalid kind
        if (null == mapTransfer.kindTransfer(request.getTrain_kind())) {
            checkErrorsList.add(new CheckErrors("TrainKindInvalid", "Train Kind is invalid"));
        }
        // duplicate stops
        List<String> nameList = new ArrayList<>();
        request.getStops().forEach(map -> nameList.add(map.get("stop_name")));
        if (nameList.stream().distinct().toList().size() != request.getStops().size()) {
            checkErrorsList.add(new CheckErrors("TrainStopsDuplicate", "Train Stops is duplicate"));
        }

        if (!checkErrorsList.isEmpty()) {
            throw new CheckException(checkErrorsList);
        }
    }

    public void ticketCreateChecked(TicketRequest request) throws CheckException {
        List<CheckErrors> checkErrorsList = new ArrayList<>();
        Train train = trainNoCheck(Integer.parseInt(request.getTrain_no()));
        Optional<Stop> from = stopRepo.findByNameAndTrainId(request.getFrom_stop(), train.getId());
        Optional<Stop> to = stopRepo.findByNameAndTrainId(request.getTo_stop(), train.getId());
        // wrong No
        if (trainRepo.findByTrainNo(Integer.parseInt(request.getTrain_no())).isEmpty()) {
            checkErrorsList.add(new CheckErrors("TrainNoNotExists", "Train No does not exists"));
        }
        // same station
        if (request.getFrom_stop().equals(request.getTo_stop()) || from.isEmpty() || to.isEmpty()) {
            checkErrorsList.add(new CheckErrors("TicketStopsInvalid", "Ticket From & To is invalid"));
        }
        // wrong stop seq
        else if (from.get().getSeq() > to.get().getSeq()) {
            checkErrorsList.add(new CheckErrors("TicketStopsInvalid", "Ticket From & To is invalid"));
        }

        if (!checkErrorsList.isEmpty()) {
            throw new CheckException(checkErrorsList);
        }
    }

    public void apiCheck(Integer trainNo) throws CheckException {
        String url = "https://petstore.swagger.io/v2/pet/" + trainNo;
        ResponseEntity<CheckRes> response = restTemplate.getForEntity(url, CheckRes.class);
        int code = response.getStatusCodeValue();
        if (code == 200) {
            CheckRes checkRes = response.getBody();
            assert checkRes != null;
            if (!checkRes.getStatus().equals("available"))
                throw new CheckException(List.of(new CheckErrors("TrainNoNotExists", "Train is not available")));
        }
    }

    public List<Stop> stopExistCheck(String via) throws CheckException {
        List<Stop> stopList = stopRepo.findByName(via);
        if (stopList.isEmpty()) {
            throw new CheckException(List.of(new CheckErrors("viaNotExists", "停靠站不存在")));
        }
        return stopList;
    }

    public Train trainNoCheck(int trainNo) throws CheckException {
        Optional<Train> train = trainRepo.findByTrainNo(trainNo);
        if (train.isEmpty()) {
            throw new CheckException(List.of(new CheckErrors("trainNoNotExists", "Train No does not exists")));
        }
        return train.get();
    }
    public void dateCheck(String date){
        // date
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //setLenient用於設置Calendar是否寬鬆解析字符串，如果為false，則嚴格解析；默認為true，寬鬆解析
            format.setLenient(false);
            log.info("input date: " +format.parse(date));

        } catch (ParseException parseException) {
            throw new TrainParameterException("Pattern","日期格式不正確 yyyy-mm-dd","takeDate");
        }
    }
}
