package com.example.Train.service.orther;

import com.example.Train.service.apiResult.TicketPriceResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@Component
public class TicketPrice {
    @Autowired
    RestTemplate restTemplate;

    @Value("${outbound.price.url}")
    public  String url ;

    public Double getTicketPrice() {
        ResponseEntity<TicketPriceResult> responseEntity = restTemplate.getForEntity(url, TicketPriceResult.class);
        return Objects.requireNonNull(responseEntity.getBody()).getString();
    }
}
