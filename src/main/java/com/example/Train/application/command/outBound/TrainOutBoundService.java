package com.example.Train.appLayer.command.outBound;

import com.example.Train.domain.aggregate.valueObj.AddTrain;
import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.err.CustomizedException;
import com.example.Train.exception.response.ErrorInfo;
import com.example.Train.domain.outbound.TrainApiResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

// TODO:Q6
@Component
@Slf4j
public class TrainOutBoundService {
    @Value("${outbound.status.url}")
    public String url;
    @Autowired
    RestTemplate restTemplate;

    public void trainApiCheck(AddTrain addTrain) throws CustomizedException {

        ResponseEntity<TrainApiResult> response = restTemplate.getForEntity(url + addTrain.getTrainNo(), TrainApiResult.class);
        int code = response.getStatusCodeValue();
        TrainApiResult trainApiResult = response.getBody();
        if(null==trainApiResult){
            throw new CustomizedException(List.of(new CheckErrors(ErrorInfo.trainOutBoundApi.getCode(), ErrorInfo.trainOutBoundApi.getErrorMessage())));
        }
        if (code == 200&&!trainApiResult.getStatus().equals("available")) {
            throw new CustomizedException(List.of(new CheckErrors(ErrorInfo.trainNotAvailable.getCode(), ErrorInfo.trainNotAvailable.getErrorMessage())));
        }
    }
//    public void apiCheck(AddTrain addTrain, List<CheckErrors> checkErrorsList) throws CustomizedException {
//        ResponseEntity<TrainApiResult> apiResultResponseEntity = trainOutBoundService.getResponse(addTrain.getTrainNo());
//        log.info(apiResultResponseEntity.toString());
//        if (200 == apiResultResponseEntity.getStatusCodeValue() && !apiResultResponseEntity.getBody().equals("available")) {
//            checkErrorsList.add(new CheckErrors(ErrorInfo.trainNotAvailable.getCode(), ErrorInfo.trainNotAvailable.getErrorMessage()));
//        }
//    }
    public ResponseEntity<TrainApiResult> getResponse(Integer trainNo) throws CustomizedException {
        try {
            return restTemplate.getForEntity(url + trainNo, TrainApiResult.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new CustomizedException(List.of(new CheckErrors(ErrorInfo.trainOutBoundApi.getCode(), ErrorInfo.trainOutBoundApi.getErrorMessage())));
        }
    }
}
