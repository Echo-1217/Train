package com.example.Train.interfa.event.exception.customerErrorMsg;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomizedException extends Exception {
    List<CheckErrors> checkErrorsList;
}
