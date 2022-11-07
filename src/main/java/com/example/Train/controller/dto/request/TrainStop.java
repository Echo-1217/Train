package com.example.Train.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainStop {
    @NotBlank
    String stopName;
    @NotEmpty
    @Pattern(regexp = "(^([0-1]?\\d|2[0-3]):([0-5]?\\d)$)", message = "時間格式錯誤")
    String stopTime;
}
