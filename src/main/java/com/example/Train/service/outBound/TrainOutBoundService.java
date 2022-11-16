package com.example.Train.service.outBound;

import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.err.CustomizedException;
import com.example.Train.exception.response.ErrorInfo;
import com.example.Train.service.apiResult.TrainApiResult;
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

    public Boolean trainApiCheck(Integer trainNo) {

        ResponseEntity<TrainApiResult> response = restTemplate.getForEntity(url + trainNo, TrainApiResult.class);
        int code = response.getStatusCodeValue();
        if (code == 200) {
            TrainApiResult trainApiResult = response.getBody();
            assert trainApiResult != null;
            return !trainApiResult.getStatus().equals("available");
        }
        return false;
    }

    public ResponseEntity<TrainApiResult> getResponse(Integer trainNo) throws CustomizedException {
        try {
            return restTemplate.getForEntity(url + trainNo, TrainApiResult.class);
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new CustomizedException(List.of(new CheckErrors(ErrorInfo.trainOutBoundApi.getCode(), ErrorInfo.trainOutBoundApi.getErrorMessage())));
        }
    }
}
