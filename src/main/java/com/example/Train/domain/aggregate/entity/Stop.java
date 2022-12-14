package com.example.Train.domain.aggregate.entity;

import com.example.Train.config.event.exception.customerErrorMsg.CheckErrors;
import com.example.Train.config.event.exception.customerErrorMsg.CustomizedException;
import com.example.Train.config.event.exception.customerErrorMsg.ErrorInfo;
import com.example.Train.domain.command.AddTrainCommand;
import com.example.Train.intfa.dto.request.ViaNameTime;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Getter
//@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRAIN_STOP")
@Slf4j
public class Stop {
    @Id
    @Column(name = "UUID")
    private String id;
    @Column(name = "TRAIN_UUID")
    private String trainId;
    @Column(name = "SEQ")
    private int seq;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TIME")
    private LocalTime time;
    @Column(name = "DELETE_FLAG")
    private String deleteFlag;

    public List<Stop> buildList(AddTrainCommand command, String trainId) throws CustomizedException {
        List<Stop> stopList = new ArrayList<>();
        AtomicInteger seq = new AtomicInteger(1);
        checkTime(command);
        checkPlace(command);
        command.getStops().forEach(via -> stopList.add(
                Stop.builder()
                        .id(RandomString.make(32).toUpperCase())
                        .trainId(trainId)
                        .seq(seq.getAndIncrement())
                        .name(via.getStopName())
                        .time(LocalTime.parse(via.getStopTime()))
                        .deleteFlag("N").build())
        );
        return stopList;
    }
//
//    public Stop(){
//
//    }


    public void checkTime(AddTrainCommand command) throws CustomizedException {
        // time incorrect
        if (!Objects.equals(command.getStops().stream().sorted(Comparator.comparing(ViaNameTime::getStopTime)).toList(), command.getStops())) {
            throw new CustomizedException(List.of(new CheckErrors(ErrorInfo.trainStopsTimeNotSorted.getCode(), ErrorInfo.trainStopsTimeNotSorted.getErrorMessage())));
        }
    }


    public void checkPlace(AddTrainCommand command) throws CustomizedException {
        try {

            // ????????? list via
            List<String> via = new ArrayList<>();
            command.getStops().forEach(viaNameTime -> via.add(viaNameTime.getStopName()));

            AtomicReference<Boolean> error = new AtomicReference<>(false);
            // duplicate stops
            if (via.stream().distinct().toList().size() != command.getStops().size()) {
                error.set(true);
                throw new CustomizedException(List.of(new CheckErrors(ErrorInfo.trainStopsDuplicate.getCode(), ErrorInfo.trainStopsDuplicate.getErrorMessage())));
            }

            List<String> place = List.of("??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????", "??????",
                    "??????", "??????", "??????", "??????", "??????", "??????", "??????");

            via.forEach(stop -> {
                if (!place.contains(stop)) {
                    error.set(true);
                }
            });

            AtomicInteger currPlace = new AtomicInteger(-1);

            int first = place.indexOf(via.get(0));
            int next = place.indexOf(via.get(1));

            // ??????????????????index
            currPlace.set(first);

            via.forEach(s -> {

                // (??????)  first > next   and  ??????????????? > ?????????
                if (first > next && place.indexOf(s) > currPlace.get()) {
                    log.info("??????");
                    // throw
                    error.set(true);
                    return;
                }
                // (??????) first < next and ??????????????? < ?????????
                if (first < next && place.indexOf(s) < currPlace.get()) {
                    log.info("??????");
                    // throw
                    error.set(true);
                    return;
                }
                // ?????????????????????
                currPlace.set(place.indexOf(s));

            });
            if (error.get().equals(true)) {
                throw new CustomizedException(List.of(new CheckErrors(ErrorInfo.trainStopsNotSorted.getCode(), ErrorInfo.trainStopsNotSorted.getErrorMessage())));
//            return new CheckErrors(ErrorInfo.trainStopsNotSorted.getCode(), ErrorInfo.trainStopsNotSorted.getErrorMessage());
            }
//        return null;
        } catch (RuntimeException exception) {
            exception.printStackTrace();

        }
    }
}
