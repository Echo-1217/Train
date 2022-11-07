package com.example.Train.service.command;

import com.example.Train.controller.dto.request.TicketRequest;
import com.example.Train.controller.dto.response.UniqueIdResponse;
import com.example.Train.model.TicketRepo;
import com.example.Train.model.entity.Ticket;
import com.example.Train.exception.err.CheckException;
import com.example.Train.service.TicketCommandService;
import com.example.Train.service.valid.TicketCreateCheck;
import com.example.Train.service.orther.TicketPrice;
import com.example.Train.service.orther.UniqueIdCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketCommandImpl implements TicketCommandService {
    @Autowired
    TicketRepo ticketRepo;
    @Autowired
    TicketPrice ticketPrice;
    @Autowired
    TicketCreateCheck ticketCreateAuth;

    @Override
    public UniqueIdResponse createTicket(TicketRequest request) throws CheckException {
        ticketCreateAuth.ticketCreatedCheck(request);
        ticketCreateAuth.dateValueCheck(request.getTake_date());
        Ticket ticket = setTicket(request);
        ticketRepo.save(ticket);
        return new UniqueIdResponse(ticket.getTrainUuid());
    }



    private Ticket setTicket(TicketRequest request) throws CheckException {
        Ticket ticket = new Ticket();
        ticket.setTickNo(new UniqueIdCreator().getTrainUid());
        ticket.setTrainUuid(ticketCreateAuth.trainNoFindCheck(Integer.parseInt(request.getTrain_no())).getId());
        ticket.setPrice(ticketPrice.getTicketPrice());
        ticket.setFromStop(request.getFrom_stop());
        ticket.setToStop(request.getTo_stop());
        ticket.setTakeDate(request.getTake_date());

        return ticket;
    }
}
