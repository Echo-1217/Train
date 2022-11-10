package com.example.Train.service.modifier;

import com.example.Train.model.entity.Train;
import com.example.Train.service.orther.NameKindTranslator;
import com.example.Train.service.orther.UniqueIdCreator;
import org.springframework.stereotype.Component;

@Component
public class TrainModifier {
    public Train buildTrain(int trainNo, String kind) {

//        train.setId(new UniqueIdCreator().getTrainUid());
//        train.setTrainNo(trainNo);
//        train.setTrainKind(NameKindTranslator.getKind(kind));
//     ======================
        return new Train(new UniqueIdCreator().getTrainUid(), trainNo, NameKindTranslator.getKind(kind));
//     ========================
        //        return Train.builder()
//                .id(new UniqueIdCreator().getTrainUid())
//                .trainNo(trainNo)
//                .trainKind(NameKindTranslator.getKind(kind))
//                .build();
    }
}
