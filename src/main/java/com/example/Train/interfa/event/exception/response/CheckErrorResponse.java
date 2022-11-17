package com.example.Train.interfa.event.exception.response;

import com.example.Train.interfa.event.exception.customerErrorMsg.CustomizedException;
import lombok.Data;

import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class CheckErrorResponse {
    private String error;
    private List<Map<String, String>> checkErrors;


    public CheckErrorResponse(CustomizedException customizedException) {
        this.error = "VALIDATE_FAILED";
        this.checkErrors = new ArrayList<>();

        customizedException.getCheckErrorsList().forEach(error -> {

            Map<String, String> stringMap = new HashMap<>();
            // 錯誤類型
            stringMap.put("code", error.getCode());
            // 錯誤訊息
            stringMap.put("message ", error.getMessage());

            this.checkErrors.add(stringMap);
        });
    }

    public CheckErrorResponse(DateTimeParseException dateTimeParseException) {
        this.error = "VALIDATE_FAILED";
        this.checkErrors = new ArrayList<>();
        // 再放入 fieldError 中
        Map<String, String> fieldMap = new HashMap<>();

        // 錯誤類型
        fieldMap.put("code", dateTimeParseException.getClass().getSimpleName());
        // 錯誤訊息
        fieldMap.put("message ", dateTimeParseException.getMessage());

        checkErrors.add(fieldMap);
    }


    public CheckErrorResponse(Exception error) {
        this.error = error.getMessage();
    }
}
