package com.example.Train.controller.dto.response.err;

import com.example.Train.model.exception.CheckException;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CheckErrorResponse {
    private String error;
    private List<Map<String, String>> checkErrors;


    public CheckErrorResponse(CheckException checkException) {
        this.error = "VALIDATE_FAILED";
        this.checkErrors = new ArrayList<>();

        checkException.getCheckErrorsList().forEach(error -> {

            Map<String, String> stringMap = new HashMap<>();
            // 錯誤類型
            stringMap.put("code", error.getCode());
            // 錯誤訊息
            stringMap.put("message ", error.getMessage());

            this.checkErrors.add(stringMap);
        });
    }
    public CheckErrorResponse(Exception error) {
        this.error = error.getMessage();
    }
}
