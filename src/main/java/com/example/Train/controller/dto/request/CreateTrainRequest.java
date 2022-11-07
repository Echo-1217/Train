package com.example.Train.controller.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainRequest {
    @NotBlank(message = "車次不可以為空")
    @JsonProperty("train_no")
    private String trainNo;
    @NotBlank(message = "車種不可以為空")
    @JsonProperty("train_kind")
    private String trainKind;
    @UniqueElements(message = "車站/到站時間不能重複")
    @NotEmpty(message = "停靠站不可以為空")
    private List<TrainStop> trainStops;

}
