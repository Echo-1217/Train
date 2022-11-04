package com.example.Train.controller.dto.response.apiResult;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckRes {
    private String id;
    private IdName category;
    private String name;
    private List photoUrls;
    private List<IdName> tags;
    private String status;
}
class IdName{
    private String id;
    private String name;
}

