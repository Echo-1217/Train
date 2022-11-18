package com.example.Train.application.command;

import com.example.Train.config.event.exception.customerErrorMsg.CustomizedException;
import com.example.Train.domain.aggregate.domainService.TicketDomainService;
import com.example.Train.domain.aggregate.entity.Ticket;
import com.example.Train.domain.command.AddTicketCommand;
import com.example.Train.infrastructure.outbound.TicketOutBoundService;
import com.example.Train.infrastructure.repo.TicketRepo;
import com.example.Train.intfa.dto.response.UniqueIdResponse;
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
    public UniqueIdResponse createTicket(AddTicketCommand command) throws CustomizedException {

        // check via
        String trainUuid = check.stopCheckAndGetTrainId(command);

        // get price
        double price = ticketOutBoundService.getTicketPrice();

        // create ticket
        Ticket ticket = new Ticket(command, trainUuid, price);

        // save in database
        ticketRepo.save(ticket);
        return new UniqueIdResponse(ticket.getTickNo());
    }

}
