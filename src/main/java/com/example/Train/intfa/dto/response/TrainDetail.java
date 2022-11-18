package com.example.Train.intfa.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@AllArgsConstructor
public class TrainDetail {
    @JsonProperty("train_no")
    private int trainNo;
    @JsonProperty("train_kind")
    private String trainKind;
}
