package com.example.Train.service.valid;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.request.TrainStop;
import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.err.CheckException;
import com.example.Train.model.TrainRepo;
import com.example.Train.service.apiResult.TrainApiResult;
import com.example.Train.service.orther.MapTransfer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Component
@Slf4j
public class TrainCreateCheck extends TrainBasicCheck {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    MapTransfer mapTransfer;
    @Autowired
    TrainRepo trainRepo;
    @Value("${outbound.status.url}")
    private String url;

    public void trainCreatedCheck(CreateTrainRequest request) throws CheckException {
        List<CheckErrors> checkErrorsList = new ArrayList<>();

        // wrong No
        if (trainRepo.findByTrainNo((Integer.parseInt(request.getTrainNo()))).isPresent()) {
            checkErrorsList.add(new CheckErrors("TrainNoExists", "Train No is exists"));
        }
        // invalid kind
        if (null == mapTransfer.kindTransfer(request.getTrainKind())) {
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
        if (!Objects.equals(request.getTrainStops().stream().sorted(Comparator.comparing(TrainStop::getStopTime)).toList(), request.getTrainStops())) {// time incorrect
            checkErrorsList.add(new CheckErrors("TrainStopTimeNotSorted", "Train Stops Time is not sorted"));
        }
        // api
        if (trainApiCheck(Integer.parseInt(request.getTrainNo()))) {
            checkErrorsList.add(new CheckErrors("TrainNoNotExists", "Train is not available"));
        }
        // return
        if (!checkErrorsList.isEmpty()) {
            throw new CheckException(checkErrorsList);
        }
    }

    private String placeCheck(CreateTrainRequest request) {
        // 初始化 list via
        List<String> via = new ArrayList<>();
        request.getTrainStops().forEach(trainStop -> via.add(trainStop.getStopName()));

        AtomicReference<String> error = new AtomicReference<>("correct");
        // duplicate stops
        if (via.stream().distinct().toList().size() != request.getTrainStops().size()) {
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

    private Boolean trainApiCheck(Integer trainNo) {

        ResponseEntity<TrainApiResult> response = restTemplate.getForEntity(url + trainNo, TrainApiResult.class);
        int code = response.getStatusCodeValue();
        if (code == 200) {
            TrainApiResult trainApiResult = response.getBody();
            assert trainApiResult != null;
            return !trainApiResult.getStatus().equals("available");
        }
        return false;
    }


}
