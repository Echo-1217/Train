package com.example.Train.intfa.dto.response;

import lombok.Builder;
import lombok.Data;

//@Getter
//@Setter
@Data
//@NoArgsConstructor
//@AllArgsConstructor
@Builder
public class StopDetail {
    private String stop_name;
    private String stop_time;

}
