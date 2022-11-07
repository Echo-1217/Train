package com.example.Train.service;

import com.example.Train.controller.dto.request.TicketRequest;
import com.example.Train.controller.dto.response.UniqueIdResponse;
import com.example.Train.exception.err.CheckException;

public interface TicketCommandService {
    UniqueIdResponse createTicket(TicketRequest request) throws CheckException;
}
