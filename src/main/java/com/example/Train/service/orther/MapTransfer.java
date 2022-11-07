package com.example.Train.service.orther;

import org.springframework.stereotype.Component;

@Component
public class MapTransfer {
    public String kindTransfer(String trainKind) {
        return switch (trainKind) {
            case "A" -> "諾亞方舟號";
            case "B" -> "霍格華茲號";
            case "霍格華茲號" -> "B";
            case "諾亞方舟號" -> "A";
            default -> null;
        };
    }
}
