package com.example.Train.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@AllArgsConstructor
public class TrainDetail {
    private int train_no;
    private String train_kind;
}
