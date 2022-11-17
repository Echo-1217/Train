package com.example.Train.domain.apiResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainApiResult {
    private String id;
    private IdName category;
    private String name;
    private List photoUrls;
    private List<IdName> tags;
    private String status;
}

