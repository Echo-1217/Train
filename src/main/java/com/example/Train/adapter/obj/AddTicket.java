package com.example.Train.adapter.obj;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddTicket {
    @JsonProperty("train_no")
    private String trainNo;
    @JsonProperty("from_stop")
    private String fromStop;
    @JsonProperty("to_stop")
    private String toStop;
    @JsonProperty("take_date")
    private String takeDate;
}
