package com.example.Train.service.command;

import com.example.Train.controller.dto.request.TicketRequest;
import com.example.Train.controller.dto.response.UUIdResponse;
import com.example.Train.model.TicketRepo;
import com.example.Train.model.entity.Ticket;
import com.example.Train.model.exception.CheckException;
import com.example.Train.service.orther.CalTicket;
import com.example.Train.service.orther.UUidCreator;
import com.example.Train.service.authentication.TicketCreateAuth;
import com.example.Train.service.authentication.TrainBasicAuth;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketCommandService {
    @Autowired
    TicketRepo ticketRepo;
    @Autowired
    CalTicket calTicket;
    @Autowired
    TicketCreateAuth ticketCreateAuth;
    @Autowired
    TrainBasicAuth basicAuth;


    public UUIdResponse createTicket(TicketRequest request) throws CheckException {
        ticketCreateAuth.ticketCreatedCheck(request);
        ticketCreateAuth.dateValueCheck(request.getTake_date());
        Ticket ticket = setTicket(request);
        ticketRepo.save(ticket);
        return new UUIdResponse(ticket.getTrainUuid());
    }

    private Ticket setTicket(TicketRequest request) throws CheckException {
        Ticket ticket = new Ticket();
        ticket.setTickNo(new UUidCreator().getTrainUUID());
        ticket.setTrainUuid(basicAuth.trainNoFindCheck(Integer.parseInt(request.getTrain_no())).getId());
        ticket.setPrice(calTicket.getTicketPrice());
        ticket.setFromStop(request.getFrom_stop());
        ticket.setToStop(request.getTo_stop());
        ticket.setTakeDate(request.getTake_date());

        return ticket;
    }
}
