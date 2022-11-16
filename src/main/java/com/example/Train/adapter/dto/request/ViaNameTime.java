package com.example.Train.adapter.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViaNameTime {
    @NotBlank
    @JsonProperty("stop_name")
    String stopName;
    @NotBlank
    @JsonProperty("stop_time")
    @Pattern(regexp = "(^([0-1]?\\d|2[0-3]):([0-5]?\\d)$)", message = "時間格式錯誤")
    String stopTime;
}
