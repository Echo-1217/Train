package com.example.Train.service.domain.valid;

import com.example.Train.controller.dto.request.CreateTrainRequest;
import com.example.Train.controller.dto.request.ViaNameTime;
import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.err.CustomizedException;
import com.example.Train.model.TrainRepo;
import com.example.Train.service.domain.orther.NameKindTranslator;
import com.example.Train.service.domain.orther.api.TrainNoApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TrainDomainService {
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    TrainRepo trainRepo;

    @Autowired
    TrainNoApi trainNoApi;

    public void trainCreatedCheck(CreateTrainRequest request) throws CustomizedException {
        List<CheckErrors> checkErrorsList = new ArrayList<>();

        // wrong No
        if (trainRepo.findByTrainNo((Integer.parseInt(request.getTrainNo()))).isPresent()) {
            checkErrorsList.add(new CheckErrors("TrainNoExists", "Train No is exists"));
        }
        // invalid kind
        if (null == NameKindTranslator.getKind(request.getTrainKind())) {
            checkErrorsList.add(new CheckErrors("TrainKindInvalid", "Train Kind is invalid"));
        }
        String placeCorrectCheck = placeCheck(request);
        // duplicate stops
        if (placeCorrectCheck.equals("duplicate")) {
            checkErrorsList.add(new CheckErrors("TrainStopsDuplicate", "Train Stops is duplicate"));
        }
        // place seq
        if (placeCorrectCheck.equals("seq")) {
            checkErrorsList.add(new CheckErrors("TrainStopsNotSorted", "Train Stops is not sorted"));
        }
        // time incorrect
        if (!Objects.equals(request.getStops().stream().sorted(Comparator.comparing(ViaNameTime::getStopTime)).toList(), request.getStops())) {
            checkErrorsList.add(new CheckErrors("TrainStopTimeNotSorted", "Train Stops Time is not sorted"));
        }
        // api
        if (trainNoApi.trainApiCheck(Integer.parseInt(request.getTrainNo()))) {
            checkErrorsList.add(new CheckErrors("TrainNoNotExists", "Train is not available"));
        }
        // return
        if (!checkErrorsList.isEmpty()) {
            throw new CustomizedException(checkErrorsList);
        }
    }

    private String placeCheck(CreateTrainRequest request) {
        // 初始化 list via
        List<String> via = new ArrayList<>();
        request.getStops().forEach(viaNameTime -> via.add(viaNameTime.getStopName()));

        AtomicReference<String> error = new AtomicReference<>("correct");
        // duplicate stops
        if (via.stream().distinct().toList().size() != request.getStops().size()) {
            error.set("duplicate");
            return error.get();
        }

        List<String> place = List.of("屏東", "高雄", "臺南", "嘉義", "彰化", "台中", "苗粟", "新竹", "桃園", "樹林",
                "板橋", "萬華", "台北", "松山", "南港", "汐止", "基隆");

        AtomicInteger currPlace = new AtomicInteger(-1);

        int first = place.indexOf(via.get(0));
        int next = place.indexOf(via.get(1));

        // 第一個地點的index
        currPlace.set(first);

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
            // 放入下一個地點
            currPlace.set(place.indexOf(s));

        });
        return error.get();
    }
}
