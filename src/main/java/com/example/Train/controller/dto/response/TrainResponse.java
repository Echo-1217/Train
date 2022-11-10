package com.example.Train.controller.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrainResponse {
    private int train_no;
    private String train_kind;
    private List<StopDetail> stopDetails;
}
