package com.example.Train.controller.dto.response.err;

import com.example.Train.model.exception.TrainParameterException;
import lombok.Data;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class FieldErrorResponse {
    private String error;
    private List<Map<String, String>> fieldError;

    // 處理 RequestBody 未通過基礎檢核所拋的 MethodArgumentNotValidException
    public FieldErrorResponse(MethodArgumentNotValidException methodArgumentNotValidException) {
        this.error = "Validate Failed";
        this.fieldError = new ArrayList<>();

        // 因為未通過基礎檢核的欄位可能不只一個
        // 所以需要呼叫 methodArgumentNotValidException.getBindingResult().getFieldErrors() 取得不符合基礎檢核的欄位
        // 再放入 fieldError 中

        methodArgumentNotValidException.getBindingResult().getFieldErrors().forEach(error -> {
            Map<String, String> fieldMap = new HashMap<>();

            // 錯誤類型，例 : NotNull 或是 NotBlank
            fieldMap.put("code", error.getCode());
            fieldMap.put("message ", error.getDefaultMessage());
            fieldMap.put("fields", error.getField());

            fieldError.add(fieldMap);
        });
    }

    // 處理 Query String 未通過基礎檢核所拋的 ConstraintViolationException
    public FieldErrorResponse(ConstraintViolationException constraintViolationException) {
        this.error = "Validate Failed";
        this.fieldError = new ArrayList<>();

        // 因為未通過基礎檢核的欄位可能不只一個
        // 所以需要呼叫 constraintViolationException.getConstraintViolations() 取得不符合基礎檢核的欄位
        // 再放入 fieldError 中

        constraintViolationException.getConstraintViolations().forEach(error -> {

            String fieldName = null;

            for (Path.Node node : error.getPropertyPath()) {
                fieldName = node.getName();
            }

            Map<String, String> map = new HashMap<>();
            // 錯誤類型，例 : NotNull 或是 NotBlank
//            map.put("code", error.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());
            map.put("message", error.getMessage());
            map.put("field", fieldName);
            fieldError.add(map);
        });
    }

    public FieldErrorResponse(MethodArgumentTypeMismatchException methodArgumentTypeMismatchException) {

        this.error = "VALIDATE_FAILED";
        this.fieldError = new ArrayList<>();
        // 再放入 fieldError 中

        Map<String, String> fieldMap = new HashMap<>();

        // 錯誤類型
        fieldMap.put("code", methodArgumentTypeMismatchException.getErrorCode());
        // 錯誤訊息
        fieldMap.put("message ", "車次必須為正整數");
        // 欄位名稱
        fieldMap.put("fields", methodArgumentTypeMismatchException.getName());

        fieldError.add(fieldMap);
    }

    public FieldErrorResponse(TrainParameterException trainParameterException) {
        this.error = "VALIDATE_FAILED";
        this.fieldError = new ArrayList<>();
        // 再放入 fieldError 中
        Map<String, String> fieldMap = new HashMap<>();

        // 錯誤類型
        fieldMap.put("code", trainParameterException.getCode());
        // 錯誤訊息
        fieldMap.put("message ", trainParameterException.getMessage());
        // 欄位名稱
        fieldMap.put("fields", trainParameterException.getFieldName());

        fieldError.add(fieldMap);
    }

    public FieldErrorResponse(DateTimeParseException dateTimeParseException) {
        this.error = "VALIDATE_FAILED";
        this.fieldError = new ArrayList<>();
        // 再放入 fieldError 中
        Map<String, String> fieldMap = new HashMap<>();

        // 錯誤類型
        fieldMap.put("code", dateTimeParseException.getClass().getSimpleName());
        // 錯誤訊息
        fieldMap.put("message ", dateTimeParseException.getMessage());
        // 欄位名稱
        fieldMap.put("fields", "stop_time");

        fieldError.add(fieldMap);
    }

    // 處理 Exception
    public FieldErrorResponse(Exception exception) {
        this.error = exception.getMessage();
    }
}
