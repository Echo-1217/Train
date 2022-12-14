package com.example.Train.domain.aggregate.domainService;

import com.example.Train.config.event.exception.customerErrorMsg.CheckErrors;
import com.example.Train.config.event.exception.customerErrorMsg.CustomizedException;
import com.example.Train.config.event.exception.customerErrorMsg.ErrorInfo;
import com.example.Train.domain.command.AddTrainCommand;
import com.example.Train.infrastructure.repo.TrainRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
//    @Autowired
//    TrainOutBoundService trainOutBoundService;


    public void singleChecked(CheckErrors... checkErrors) throws CustomizedException {
        throw new CustomizedException(Arrays.stream(checkErrors).filter(Objects::nonNull).toList());
    }

    public void summaryCheck(AddTrainCommand addTrainCommand, CheckErrors... checkErrors) throws CustomizedException {

        List<CheckErrors> checkErrorsList = new ArrayList<>(Arrays.stream(checkErrors).filter(Objects::nonNull).toList());

//        // wrong No
//        if (trainRepo.findByTrainNo((addTrainCommand.getTrainNo())).isPresent()) {
//            checkErrorsList.add(new CheckErrors("TrainNoExists", "Train No is exists"));
//        }
        trainNoCheck(addTrainCommand, checkErrorsList);

//        String placeCorrectCheck = placeCheck(addTrainCommand);
        // duplicate stops
//        if (placeCorrectCheck.equals("duplicate")) {
//            checkErrorsList.add(new CheckErrors("TrainStopsDuplicate", "Train Stops is duplicate"));
//        }
        // place seq
//        if (placeCorrectCheck.equals("seq")) {
//            checkErrorsList.add(new CheckErrors("TrainStopsNotSorted", "Train Stops is not sorted"));
//        }
//        placeCheck(addTrainCommand,checkErrorsList);

        // api
//        ResponseEntity<TrainApiResult> apiResultResponseEntity = trainOutBoundService.getResponse(addTrainCommand.getTrainNo());
//        if (200 == apiResultResponseEntity.getStatusCodeValue() && !Objects.equals(apiResultResponseEntity.getBody(), "available")) {
//            checkErrorsList.add(new CheckErrors("TrainNoNotExists", "Train is not available"));
//        }

//        apiCheck(addTrainCommand, checkErrorsList);

        // return
        if (!checkErrorsList.isEmpty()) {
            throw new CustomizedException(checkErrorsList);
        }
    }

    public void trainNoCheck(AddTrainCommand addTrainCommand, List<CheckErrors> checkErrorsList) {
        if (trainRepo.findByTrainNo((addTrainCommand.getTrainNo())).isPresent()) {
            checkErrorsList.add(new CheckErrors(ErrorInfo.trainNoExists.getCode(), ErrorInfo.trainNoExists.getErrorMessage()));
        }
    }


//    private void placeCheck(AddTrainCommand addTrain, List<CheckErrors> checkErrorsList) {
//        // ????????? list via
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
//        List<String> place = List.of("??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????",
//                "??????", "??????", "??????", "??????", "??????", "??????", "??????");
//
//        AtomicInteger currPlace = new AtomicInteger(-1);
//
//        int first = place.indexOf(via.get(0));
//        int next = place.indexOf(via.get(1));
//
//        // ??????????????????index
//        currPlace.set(first);
//
//        via.forEach(s -> {
//
//            // (??????)  first > next   and  ??????????????? > ?????????
//            if (first > next && place.indexOf(s) > currPlace.get()) {
//                // throw
//                error.set("seq");
//                checkErrorsList.add(new CheckErrors("TrainStopsNotSorted", "Train Stops is not sorted"));
//                return;
//            }
//            // (??????) first < next and ??????????????? < ?????????
//            if (first < next && place.indexOf(s) < currPlace.get()) {
//                // throw
//                error.set("seq");
//                checkErrorsList.add(new CheckErrors("TrainStopsNotSorted", "Train Stops is not sorted"));
//                return;
//            }
//            // ?????????????????????
//            currPlace.set(place.indexOf(s));
//
//        });
//        error.get();
//    }
}
