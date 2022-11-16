package com.example.Train.service.appLayer.command;

import com.example.Train.controller.dto.request.TicketRequest;
import com.example.Train.controller.dto.response.UniqueIdResponse;
import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.err.CustomizedException;
import com.example.Train.model.addObj.AddTicket;
import com.example.Train.model.entity.Ticket;
import com.example.Train.model.entity.Train;
import com.example.Train.model.repository.TicketRepo;
import com.example.Train.service.domainLayer.data.TicketDomainService;
import com.example.Train.service.outBound.TicketOutBoundService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TicketCommandService {

    @Autowired
    TicketDomainService check;
    @Autowired
    TicketRepo ticketRepo;
    @Autowired
    TicketOutBoundService ticketOutBoundService;

    @Transactional
    public UniqueIdResponse createTicket(TicketRequest request) throws CustomizedException {

        AddTicket addTicket = new ObjectMapper().convertValue(request, AddTicket.class);

        // check same via
        CheckErrors sameViaCheck = new Ticket().sameViaCheck(addTicket);

        // check train exist
        Train train = check.trainNoCheckAndGet(addTicket);

        // check via
        check.summaryCheck(addTicket, train, sameViaCheck);

        // get price
        double price = ticketOutBoundService.getTicketPrice();

        // create ticket
        Ticket ticket = new Ticket(addTicket, train, price);

        // save in database
        ticketRepo.save(ticket);
        return new UniqueIdResponse(ticket.getTickNo());
    }

}
