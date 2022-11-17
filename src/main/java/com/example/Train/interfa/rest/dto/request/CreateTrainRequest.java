package com.example.Train.interfa.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @NotEmpty(message = "停靠站不可以為空")
    private List<ViaNameTime> stops;

}
