package com.example.Train.infrastructure.outbound.service;

import com.example.Train.infrastructure.outbound.result.TicketPriceResult;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

// TODO:Q6
@Component
@NoArgsConstructor
public class TicketOutBoundService {
    @Autowired
    RestTemplate restTemplate;

    @Value("${outbound.price.url}")
    String url;

    public Double getTicketPrice() {
        ResponseEntity<TicketPriceResult> responseEntity = restTemplate.getForEntity(url, TicketPriceResult.class);
        if (null != responseEntity.getBody()) {
            return responseEntity.getBody().getString();
        }
        return 0.0;
    }
}
