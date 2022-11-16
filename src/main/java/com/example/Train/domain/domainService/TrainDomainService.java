package com.example.Train.domain.domainService;

import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.err.CustomizedException;
import com.example.Train.exception.response.ErrorInfo;
import com.example.Train.adapter.obj.AddTrain;
import com.example.Train.adapter.repository.TrainRepo;
import com.example.Train.domain.apiResult.TrainApiResult;
import com.example.Train.application.outBound.TrainOutBoundService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// TODO: Q5
@Component
@Slf4j
public class TrainDomainService {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    TrainOutBoundService trainOutBoundService;


    public void singleChecked(CheckErrors... checkErrors) throws CustomizedException {
        throw new CustomizedException(Arrays.stream(checkErrors).filter(Objects::nonNull).toList());
    }

    public void summaryCheck(AddTrain addTrain, CheckErrors... checkErrors) throws Exception {
        List<CheckErrors> checkErrorsList = new ArrayList<>();

        checkErrorsList.addAll(Arrays.stream(checkErrors).filter(Objects::nonNull).toList());

//        // wrong No
//        if (trainRepo.findByTrainNo((addTrain.getTrainNo())).isPresent()) {
//            checkErrorsList.add(new CheckErrors("TrainNoExists", "Train No is exists"));
//        }
        trainNoCheck(addTrain, checkErrorsList);

//        String placeCorrectCheck = placeCheck(addTrain);
        // duplicate stops
//        if (placeCorrectCheck.equals("duplicate")) {
//            checkErrorsList.add(new CheckErrors("TrainStopsDuplicate", "Train Stops is duplicate"));
//        }
        // place seq
//        if (placeCorrectCheck.equals("seq")) {
//            checkErrorsList.add(new CheckErrors("TrainStopsNotSorted", "Train Stops is not sorted"));
//        }
//        placeCheck(addTrain,checkErrorsList);

        // TODO:Q6
        // api
//        ResponseEntity<TrainApiResult> apiResultResponseEntity = trainOutBoundService.getResponse(addTrain.getTrainNo());
//        if (200 == apiResultResponseEntity.getStatusCodeValue() && !Objects.equals(apiResultResponseEntity.getBody(), "available")) {
//            checkErrorsList.add(new CheckErrors("TrainNoNotExists", "Train is not available"));
//        }
        apiCheck(addTrain, checkErrorsList);

        // return
        if (!checkErrorsList.isEmpty()) {
            throw new CustomizedException(checkErrorsList);
        }
    }

    public void trainNoCheck(AddTrain addTrain, List<CheckErrors> checkErrorsList) {
        if (trainRepo.findByTrainNo((addTrain.getTrainNo())).isPresent()) {
            checkErrorsList.add(new CheckErrors(ErrorInfo.trainNoExists.getCode(), ErrorInfo.trainNoExists.getErrorMessage()));
        }
    }

    public void apiCheck(AddTrain addTrain, List<CheckErrors> checkErrorsList) throws CustomizedException {
            ResponseEntity<TrainApiResult> apiResultResponseEntity = trainOutBoundService.getResponse(addTrain.getTrainNo());
            log.info(apiResultResponseEntity.toString());
            if (200 == apiResultResponseEntity.getStatusCodeValue() && !apiResultResponseEntity.getBody().equals("available")) {
                checkErrorsList.add(new CheckErrors(ErrorInfo.trainNotAvailable.getCode(), ErrorInfo.trainNotAvailable.getErrorMessage()));
            }
    }


//    private void placeCheck(AddTrain addTrain, List<CheckErrors> checkErrorsList) {
//        // 初始化 list via
//        List<String> via = new ArrayList<>();
//        addTrain.getStops().forEach(viaNameTime -> via.add(viaNameTime.getStopName()));
//
//        AtomicReference<String> error = new AtomicReference<>("correct");
//        // duplicate stops
//        if (via.stream().distinct().toList().size() != addTrain.getStops().size()) {
//            error.set("duplicate");
//            checkErrorsList.add(new CheckErrors("TrainStopsDuplicate", "Train Stops is duplicate"));
//            error.get();
//            return;
//        }
//
//        List<String> place = List.of("屏東", "高雄", "臺南", "嘉義", "彰化", "台中", "苗粟", "新竹", "桃園", "樹林",
//                "板橋", "萬華", "台北", "松山", "南港", "汐止", "基隆");
//
//        AtomicInteger currPlace = new AtomicInteger(-1);
//
//        int first = place.indexOf(via.get(0));
//        int next = place.indexOf(via.get(1));
//
//        // 第一個地點的index
//        currPlace.set(first);
//
//        via.forEach(s -> {
//
//            // (南下)  first > next   and  下一個地點 > 前一個
//            if (first > next && place.indexOf(s) > currPlace.get()) {
//                // throw
//                error.set("seq");
//                checkErrorsList.add(new CheckErrors("TrainStopsNotSorted", "Train Stops is not sorted"));
//                return;
//            }
//            // (北上) first < next and 下一個地點 < 前一個
//            if (first < next && place.indexOf(s) < currPlace.get()) {
//                // throw
//                error.set("seq");
//                checkErrorsList.add(new CheckErrors("TrainStopsNotSorted", "Train Stops is not sorted"));
//                return;
//            }
//            // 放入下一個地點
//            currPlace.set(place.indexOf(s));
//
//        });
//        error.get();
//    }
}
