package com.example.Train.service.modifier;

import com.example.Train.controller.dto.request.ViaNameTime;
import com.example.Train.model.entity.Stop;
import com.example.Train.service.orther.UniqueIdCreator;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class StopModifier {

    public List<Stop> setAndGetStopList(List<ViaNameTime> viaList, String trainId) {
        List<Stop> stopList = new ArrayList<>();
        AtomicInteger seq = new AtomicInteger(1);
        viaList.forEach(via ->
                stopList.add(new Stop(
                        new UniqueIdCreator().getTrainUid(),
                        trainId,
                        seq.getAndIncrement(),
                        via.getStopName(),
                        LocalTime.parse(via.getStopTime()),
                        "N")));
        return stopList;
    }
}
