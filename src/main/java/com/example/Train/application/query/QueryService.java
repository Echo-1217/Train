package com.example.Train.application.query;

import com.example.Train.config.event.exception.customerErrorMsg.CheckErrors;
import com.example.Train.config.event.exception.customerErrorMsg.CustomizedException;
import com.example.Train.config.event.exception.customerErrorMsg.ErrorInfo;
import com.example.Train.domain.aggregate.entity.Train;
import com.example.Train.domain.command.QueryStopCommand;
import com.example.Train.domain.command.QueryTrainCommand;
import com.example.Train.infrastructure.repo.TrainRepo;
import com.example.Train.intfa.dto.response.StopDetail;
import com.example.Train.intfa.dto.response.TrainDetail;
import com.example.Train.intfa.dto.response.TrainResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QueryService {

    @Autowired
    TrainRepo trainRepo;

    public TrainResponse getTrainStopsByTrainNO(QueryTrainCommand command) throws Exception {
//        List<Map<String, ?>> dataList = trainRepo.findDataByTrainNo(command.getTrainNo());
        Optional<Train> trainOptional = trainRepo.findByTrainNo(command.getTrainNo());
        List<StopDetail> stopDetails = new ArrayList<>();

        if (trainOptional.isEmpty()) {
            throw new CustomizedException(List.of(new CheckErrors(ErrorInfo.trainNoNotExists.getCode(), ErrorInfo.trainNoNotExists.getErrorMessage())));
        }

        DateTimeFormatter df = DateTimeFormatter.ofPattern("HH:mm");

        trainOptional.get().getStopList().forEach(stop ->
                stopDetails.add(StopDetail.builder()
                        .stop_name(stop.getName())
                        .stop_time(df.format(stop.getTime()))
                        .build()));

        return TrainResponse.builder()
                .train_no(command.getTrainNo())
                .train_kind(Train.NameKindTranslator.getName(trainOptional.get().getTrainKind()))
                .stopDetails(stopDetails)
                .build();
    }

    public List<TrainDetail> getTrainStopsByVia(QueryStopCommand command) throws CustomizedException {
        List<TrainDetail> details = new ArrayList<>();

        List<Train> trainList = trainRepo.findAll();

        if (trainList.isEmpty()) {
            throw new CustomizedException(List.of(new CheckErrors(ErrorInfo.stopNameNotFound.getCode(), ErrorInfo.stopNameNotFound.getErrorMessage())));
        }

        trainList.forEach(train -> train.getStopList().forEach(stop -> {
            if (stop.getName().equals(command.getVia().trim())) {
                details.add(TrainDetail.builder()
                        .trainNo(train.getTrainNo())
                        .trainKind(Train.NameKindTranslator.getName(train.getTrainKind()))
                        .build());
            }
        }));

        return details;
    }
}
