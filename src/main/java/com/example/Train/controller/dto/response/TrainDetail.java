package com.example.Train.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TrainDetail {
    private int train_no;
    private String train_kind;
}
