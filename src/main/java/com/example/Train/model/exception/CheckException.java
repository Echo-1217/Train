package com.example.Train.model.exception;

import com.example.Train.model.entity.CheckErrors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CheckException extends Exception {
    List<CheckErrors> checkErrorsList;
}
