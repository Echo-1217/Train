package com.example.Train.service.orther;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UUidCreator {
    public String getTrainUUID(){
        return java.util.UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
