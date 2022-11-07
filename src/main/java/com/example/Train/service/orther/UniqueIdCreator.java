package com.example.Train.service.orther;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UniqueIdCreator {
    public String getTrainUid(){
        return java.util.UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }
}
