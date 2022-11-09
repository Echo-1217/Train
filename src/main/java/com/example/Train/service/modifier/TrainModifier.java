package com.example.Train.service.modifier;

import com.example.Train.model.entity.Train;
import com.example.Train.service.orther.NameKindTranslator;
import com.example.Train.service.orther.UniqueIdCreator;
import org.springframework.stereotype.Component;

@Component
public class TrainModifier {
    public Train setAndGetTrain(int trainNo, String kind) {
        Train train = new Train();
        train.setId(new UniqueIdCreator().getTrainUid());
        train.setTrainNo(trainNo);
        train.setTrainKind(NameKindTranslator.getKind(kind));

        return train;
    }
}
