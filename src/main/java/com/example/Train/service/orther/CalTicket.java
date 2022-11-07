package com.example.Train.service.orther;

import com.example.Train.controller.dto.response.apiResult.TicketPriceRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class CalTicket {
    @Autowired
    RestTemplate restTemplate;

    public Double getTicketPrice() {
        String url = "https://petstore.swagger.io/v2/store/inventory";
        ResponseEntity<TicketPriceRes> responseEntity = restTemplate.getForEntity(url, TicketPriceRes.class);
        return Objects.requireNonNull(responseEntity.getBody()).getString();
    }
}
