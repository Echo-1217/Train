package com.example.Train.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateRequest {
    @NotBlank(message = "車次不可以為空")
    private String train_no;
    @NotBlank(message = "車種不可以為空")
    private String train_kind;
    @NotEmpty(message = "停靠站不可以為空")
    private List<Map<String, String>> stops;

}
