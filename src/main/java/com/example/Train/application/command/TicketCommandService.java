package com.example.Train.appLayer.command;

import com.example.Train.interfaceLayer.rest.dto.request.TicketRequest;
import com.example.Train.interfaceLayer.rest.dto.response.UniqueIdResponse;
import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.err.CustomizedException;
import com.example.Train.domain.aggregate.valueObj.AddTicket;
import com.example.Train.domain.aggregate.entity.Ticket;
import com.example.Train.domain.aggregate.entity.Train;
import com.example.Train.infrastructLayer.TicketRepo;
import com.example.Train.domain.aggregate.domainService.TicketDomainService;
import com.example.Train.appLayer.command.outBound.TicketOutBoundService;
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
