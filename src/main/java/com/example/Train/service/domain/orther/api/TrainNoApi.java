package com.example.Train.service.domain.orther.api;

import com.example.Train.service.apiResult.TrainApiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class TrainNoApi {
    @Autowired
    RestTemplate restTemplate;

    @Value("${outbound.status.url}")
    public String url;

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
}
