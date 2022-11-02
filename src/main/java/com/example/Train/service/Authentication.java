package com.example.Train.service;

import com.example.Train.controller.dto.request.CreateRequest;
import com.example.Train.controller.dto.response.CheckRes;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.TrainErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Comparator;
import java.util.Objects;

@Component
public class Authentication {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    RestTemplate restTemplate;
    public Boolean timeChecked(CreateRequest request) {
        return Objects.equals(request.getStops().stream().sorted(Comparator.comparing(t -> t.get("stop_time"),
                                Comparator.nullsLast(Comparator.naturalOrder())))//  將空元素被認為大於非空元素
                        .toList()
                , request.getStops());
    }

    public void trainChecked(CreateRequest request) throws NoSuchMethodException {
        if(trainRepo.findByTrainNo(Integer.parseInt(request.getTrain_no())).isPresent()){

        }
    }

    public void apiCheck(Integer trainNo) throws Exception {
        String url = "https://petstore.swagger.io/v2/pet/" + trainNo;
        ResponseEntity<CheckRes> response = restTemplate.getForEntity(url, CheckRes.class);
        int code = response.getStatusCodeValue();
        if (code == 200) {
            CheckRes checkRes = response.getBody();
            if (!checkRes.getStatus().equals("available")) throw new TrainErrorException("Train is not available","trainNo","TrainNoNotExists");
        }
    }
}
