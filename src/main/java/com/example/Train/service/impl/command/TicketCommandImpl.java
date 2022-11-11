package com.example.Train.service.impl.command;

import com.example.Train.controller.dto.request.TicketRequest;
import com.example.Train.controller.dto.response.UniqueIdResponse;
import com.example.Train.exception.err.CustomizedException;
import com.example.Train.model.TicketRepo;
import com.example.Train.model.entity.Ticket;
import com.example.Train.service.infs.TicketCommandService;
import com.example.Train.service.domain.modifier.TicketModifier;
import com.example.Train.service.domain.valid.TicketDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TicketCommandImpl implements TicketCommandService {

    @Autowired
    TicketDomainService check;
    @Autowired
    TicketModifier ticketModifier;
    @Autowired
    TicketRepo ticketRepo;

    @Override
    @Transactional
    public UniqueIdResponse createTicket(TicketRequest request) throws CustomizedException {
        check.ticketCreatedCheck(request);
        check.dateValueCheck(request.getTake_date());
        Ticket ticket = ticketModifier.buildTicket(request.getTrain_no(), request.getFrom_stop(), request.getTo_stop(), request.getTake_date());
        ticketRepo.save(ticket);
        return new UniqueIdResponse(ticket.getTickNo());
    }

}
