package com.example.Train.controller.dto.response;

import com.example.Train.model.entity.TrainErrorException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorResponse {
    private List<Map<String, String>> fieldError;
    private String error;

    // 處理 RequestBody 未通過基礎檢核所拋的 MethodArgumentNotValidException
    public ErrorResponse(MethodArgumentNotValidException e) {
        this.error="Validate Failed";
        this.fieldError = new ArrayList<>();

        // 因為未通過基礎檢核的欄位可能不只一個
        // 所以需要呼叫 e.getBindingResult().getFieldErrors() 取得不符合基礎檢核的欄位
        // 再放入 fieldError 中

        e.getBindingResult().getFieldErrors().forEach(m -> {
            Map<String, String> fieldMap = new HashMap<>();

            // 欄位名稱
            fieldMap.put("fields", m.getField());

            // 錯誤類型，例 : NotNull 或是 NotBlank
            fieldMap.put("code", m.getCode());

            // 錯誤訊息，例 : 年齡不可為空
            fieldMap.put("message ", m.getDefaultMessage());

            fieldError.add(fieldMap);
        });
    }

    // 處理 Query String 未通過基礎檢核所拋的 ConstraintViolationException
    public ErrorResponse(ConstraintViolationException e) {
        this.error="Validate Failed";
        this.fieldError = new ArrayList<>();

        // 因為未通過基礎檢核的欄位可能不只一個
        // 所以需要呼叫 e.getConstraintViolations() 取得不符合基礎檢核的欄位
        // 再放入 fieldError 中

        e.getConstraintViolations().forEach(c -> {

            String fieldName = null;

            for (Path.Node node : c.getPropertyPath()) {
                fieldName = node.getName();
            }

            Map<String, String> map = new HashMap<>();
            // 錯誤類型，例 : NotNull 或是 NotBlank
            map.put("code", c.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName());

            // 錯誤訊息，例 : 值不可為空
            map.put("message", c.getMessage());

            // 欄位名稱
            map.put("field", fieldName);

            fieldError.add(map);
        });
    }

    public ErrorResponse(MethodArgumentTypeMismatchException e) {

        this.error="VALIDATE_FAILED";
        this.fieldError = new ArrayList<>();
        // 再放入 fieldError 中


            Map<String, String> fieldMap = new HashMap<>();

            // 欄位名稱
            fieldMap.put("fields", e.getName());

            // 錯誤類型
            fieldMap.put("code",e.getErrorCode());

            // 錯誤訊息
            fieldMap.put("message ", "車次必須為正整數");

            fieldError.add(fieldMap);

    }
    public ErrorResponse(TrainErrorException e) {

        this.error="VALIDATE_FAILED";
        this.fieldError = new ArrayList<>();

        // 再放入 fieldError 中

        Map<String, String> fieldMap = new HashMap<>();

        // 欄位名稱
        fieldMap.put("fields", e.getFieldName());

        // 錯誤類型
        fieldMap.put("code",e.getCode());

        // 錯誤訊息
        fieldMap.put("message ", e.getMessage());

        fieldError.add(fieldMap);

    }
    // 處理 Exception
    public ErrorResponse(Exception e) {
        this.error = e.getMessage();
    }

    public List<Map<String, String>> getFieldError() {
        return fieldError;
    }

    public void setFieldError(List<Map<String, String>> fieldError) {
        this.fieldError = fieldError;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

}
