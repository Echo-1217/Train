package com.example.Train.service;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.request.TicketRequest;
import com.example.Train.controller.dto.response.apiResult.CheckRes;
import com.example.Train.model.StopRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.Stop;
import com.example.Train.model.entity.Train;
import com.example.Train.model.exception.CheckErrors;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

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

    public void trainCreatedCheck(CreateTrainRequest request) throws CheckException {
        List<CheckErrors> checkErrorsList = new ArrayList<>();

        // wrong No
        if (trainRepo.findByTrainNo(Integer.parseInt(request.getTrain_no())).isPresent()) {
            checkErrorsList.add(new CheckErrors("TrainNoExists", "Train No is exists"));
        }
        // invalid kind
        if (null == mapTransfer.kindTransfer(request.getTrain_kind())) {
            checkErrorsList.add(new CheckErrors("TrainKindInvalid", "Train Kind is invalid"));
        }
        // duplicate stops
        if (placeCheck(request).equals("duplicate")) {
            checkErrorsList.add(new CheckErrors("TrainStopsDuplicate", "Train Stops is duplicate"));
        }
        // place seq
        if (placeCheck(request).equals("seq")) {
            checkErrorsList.add(new CheckErrors("TrainStopsNotSorted", "Train Stops is not sorted"));
        }

        // time seq
        if (!Objects.equals(request.getStops().stream().sorted(Comparator.comparing(t -> t.get("stop_time"),
                                Comparator.nullsLast(Comparator.naturalOrder())))//  將空元素被認為大於非空元素
                        .toList()
                , request.getStops())) {// time incorrect
            checkErrorsList.add(new CheckErrors("TrainStopTimeNotSorted", "Train Stops Time is not sorted"));
        }
        // return
        if (!checkErrorsList.isEmpty()) {
            throw new CheckException(checkErrorsList);
        }
    }

    public void ticketCreatedCheck(TicketRequest request) throws CheckException {
        List<CheckErrors> checkErrorsList = new ArrayList<>();
        Train train = trainNoFindCheck(Integer.parseInt(request.getTrain_no()));
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

    public void trainApiCheck(Integer trainNo) throws CheckException {
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

    public Train trainNoFindCheck(int trainNo) throws CheckException {
        Optional<Train> train = trainRepo.findByTrainNo(trainNo);
        if (train.isEmpty()) {
            throw new CheckException(List.of(new CheckErrors("trainNoNotExists", "Train No does not exists")));
        }
        return train.get();
    }

    public void dateValueCheck(String date) {
        // date
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //setLenient用於設置Calendar是否寬鬆解析字符串，如果為false，則嚴格解析；默認為true，寬鬆解析
            format.setLenient(false);
            log.info("input date: " + format.parse(date));

        } catch (ParseException parseException) {
            throw new TrainParameterException("Pattern", "日期格式不正確 yyyy-mm-dd", "takeDate");
        }
    }

    public String placeCheck(CreateTrainRequest request) {
        // 初始化 list via
        List<String> via = new ArrayList<>();
        request.getStops().forEach(map -> via.add(map.get("stop_name")));

        AtomicReference<String> error = new AtomicReference<>("correct");
        // duplicate stops
        if (via.stream().distinct().toList().size() != request.getStops().size()) {
            error.set("duplicate");
            return error.get();
        }

        List<String> place = List.of("屏東", "高雄", "臺南", "嘉義", "彰化", "台中", "苗粟", "新竹", "桃園", "樹林",
                "板橋", "萬華", "台北", "松山", "南港", "汐止", "基隆");

        AtomicInteger currPlace = new AtomicInteger(-1);

        // 第一個地點的index
        currPlace.set(place.indexOf(via.get(0)));
        int first = currPlace.get();
        int next = place.indexOf(via.get(1));


        via.forEach(s -> {
            // (南下)  first > next   and  下一個地點 > 前一個
            if (first > next && place.indexOf(s) > currPlace.get()) {
                // throw
                error.set("seq");
                return;
            }
            // (北上) first < next and 下一個地點 < 前一個
            if (first < next && place.indexOf(s) < currPlace.get()) {
                // throw
                error.set("seq");
                return;
            }
            currPlace.set(place.indexOf(s));
        });
        return error.get();
    }
}
