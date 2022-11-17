package com.example.Train.application.command;

import com.example.Train.domain.command.AddTicketCommand;
import com.example.Train.interfa.rest.dto.request.TicketRequest;
import com.example.Train.interfa.rest.dto.response.UniqueIdResponse;
import com.example.Train.interfa.event.exception.customerErrorMsg.CheckErrors;
import com.example.Train.interfa.event.exception.customerErrorMsg.CustomizedException;
import com.example.Train.domain.aggregate.entity.Ticket;
import com.example.Train.domain.aggregate.entity.Train;
import com.example.Train.infrastructure.TicketRepo;
import com.example.Train.domain.aggregate.domainService.TicketDomainService;
import com.example.Train.application.command.outBound.TicketOutBoundService;
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

        AddTicketCommand command = new ObjectMapper().convertValue(request, AddTicketCommand.class);

        // check same via
//        CheckErrors sameViaCheck = new Ticket().sameViaCheck(command);

        // check train exist
        Train train = check.trainNoCheckAndGet(command);

        // check via
//        check.summaryCheck(command, train, sameViaCheck);
        check.summaryCheck(command, train);

        // get price
        double price = ticketOutBoundService.getTicketPrice();

        // create ticket
        Ticket ticket = new Ticket(command, train, price);

        // save in database
        ticketRepo.save(ticket);
        return new UniqueIdResponse(ticket.getTickNo());
    }

}
