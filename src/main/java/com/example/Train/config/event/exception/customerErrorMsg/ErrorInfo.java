package com.example.Train.config.event.exception.customerErrorMsg;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public enum ErrorInfo {
    trainNoNotExists("TrainNoNotExists", "Train No does not exists"),
    ticketStopsInvalid("TicketStopsInvalid", "Ticket From & To is invalid"),
    ticketStopsNotExist("TicketStopsNotExist", "Ticket From or To does not exist"),
    ticketSameVia("tickSameVia", "Same stops"),
//    stopsNameError("StopsNameError", "Stop Name is Not Exist"),

    trainNotAvailable("TrainNotAvailable", "Train is not available"),
    trainNoExists("TrainNoExists", "Train No is exists"),
    trainKindInvalid("TrainKindInvalid", "Train Kind is invalid"),
    trainStopsDuplicate("TrainStopsDuplicate", "Train Stops is duplicate"),
    trainStopsTimeNotSorted("TrainStopsTimeNotSorted", "Train Stops time is not sorted"),
    //    trainStopNameNoExists("TrainStopNameNoExists", "Train Stop Name is not exists"),
    trainStopsNotSorted("TrainStopsNotSorted", "Train Stops name is not sorted"),
    //    trainNoNotExistsCN("","車次不存在"),
//    trainNameNotExistsCN("","站名不存在"),
    ticketOutBoundApi("ticketOutBoundApi", "the outBound error"),
    trainOutBoundApi("trainOutBoundApi", "the outBound error");
    private String code;
    private String errorMessage;

    ErrorInfo(String code, String errorMessage) {
        this.code = code;
        this.errorMessage = errorMessage;
    }

}
