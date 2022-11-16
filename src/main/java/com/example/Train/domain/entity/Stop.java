package com.example.Train.domain.entity;

import com.example.Train.adapter.dto.request.ViaNameTime;
import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.response.ErrorInfo;
import com.example.Train.adapter.obj.AddTrain;
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
@Setter
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

    public List<Stop> buildList(AddTrain addTrain, Train train) {
        List<Stop> stopList = new ArrayList<>();
        AtomicInteger seq = new AtomicInteger(1);

        addTrain.getStops().forEach(via -> stopList.add(
                Stop.builder()
                        .id(RandomString.make(32).toUpperCase())
                        .trainId(train.getId())
                        .seq(seq.getAndIncrement())
                        .name(via.getStopName())
                        .time(LocalTime.parse(via.getStopTime()))
                        .deleteFlag("N").build())
        );
        return stopList;
    }


    public CheckErrors checkTime(AddTrain addTrain) {
        // time incorrect
        if (!Objects.equals(addTrain.getStops().stream().sorted(Comparator.comparing(ViaNameTime::getStopTime)).toList(), addTrain.getStops())) {
            return (new CheckErrors(ErrorInfo.trainStopsTimeNotSorted.getCode(), ErrorInfo.trainStopsTimeNotSorted.getErrorMessage()));
        }
        return null;
    }


    public CheckErrors placeCheck(AddTrain addTrain) {

        // 初始化 list via
        List<String> via = new ArrayList<>();
        addTrain.getStops().forEach(viaNameTime -> via.add(viaNameTime.getStopName()));

        AtomicReference<Boolean> error = new AtomicReference<>(false);
        // duplicate stops
        if (via.stream().distinct().toList().size() != addTrain.getStops().size()) {
            return (new CheckErrors(ErrorInfo.trainStopsDuplicate.getCode(), ErrorInfo.trainStopsDuplicate.getErrorMessage()));
        }

        List<String> place = List.of("屏東", "高雄", "臺南", "嘉義", "彰化", "台中", "苗粟", "新竹", "桃園", "樹林",
                "板橋", "萬華", "台北", "松山", "南港", "汐止", "基隆");

        AtomicInteger currPlace = new AtomicInteger(-1);

        int first = place.indexOf(via.get(0));
        int next = place.indexOf(via.get(1));

        // 第一個地點的index
        currPlace.set(first);

        via.forEach(s -> {

            // (南下)  first > next   and  下一個地點 > 前一個
            if (first > next && place.indexOf(s) > currPlace.get()) {
                log.info("南下");
                // throw
                error.set(true);
                return;
            }
            // (北上) first < next and 下一個地點 < 前一個
            if (first < next && place.indexOf(s) < currPlace.get()) {
                log.info("北上");
                // throw
                error.set(true);
                return;
            }
            // 放入下一個地點
            currPlace.set(place.indexOf(s));

        });
        if (error.get().equals(true)) {
            return new CheckErrors(ErrorInfo.trainStopsNotSorted.getCode(), ErrorInfo.trainStopsNotSorted.getErrorMessage());
        }
        return null;
    }
}
