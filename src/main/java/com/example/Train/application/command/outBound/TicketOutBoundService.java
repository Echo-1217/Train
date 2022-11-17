package com.example.Train.application.command.outBound;

import com.example.Train.domain.outbound.TicketPriceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

// TODO:Q6
@Component
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
