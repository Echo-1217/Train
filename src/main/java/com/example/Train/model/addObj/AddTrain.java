package com.example.Train.model.addObj;

import com.example.Train.controller.dto.request.ViaNameTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddTrain {
    @JsonProperty("train_no")
    private int trainNo;
    @JsonProperty("train_kind")
    private String trainKind;
    private List<ViaNameTime> stops;
}
