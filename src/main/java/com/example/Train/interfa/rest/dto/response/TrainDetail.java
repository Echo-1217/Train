package com.example.Train.interfa.rest.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@AllArgsConstructor
public class TrainDetail {
    private int train_no;
    private String train_kind;
}
