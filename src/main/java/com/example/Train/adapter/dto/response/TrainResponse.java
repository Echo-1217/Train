package com.example.Train.adapter.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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