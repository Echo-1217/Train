package com.example.Train.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainRequest {
    @NotBlank(message = "車次不可以為空")
    private String train_no;
    @NotBlank(message = "車種不可以為空")
    private String train_kind;
    @UniqueElements(message = "車站/到站時間不能重複")
    @NotEmpty(message = "停靠站不可以為空")
    private List<Map<String, String>> stops;

}
