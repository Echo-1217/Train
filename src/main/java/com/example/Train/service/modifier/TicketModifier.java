package com.example.Train.service.modifier;

import com.example.Train.exception.err.CheckException;
import com.example.Train.model.entity.Ticket;
import com.example.Train.service.orther.TicketPrice;
import com.example.Train.service.orther.UniqueIdCreator;
import com.example.Train.service.valid.TicketCreateCheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketModifier {
    @Autowired
    TicketPrice ticketPrice;
    @Autowired
    TicketCreateCheck check;

    public Ticket setAndGetTicket(String trainNo, String fromStop, String toStop, String date) throws CheckException {
        Ticket ticket = new Ticket();
        ticket.setTickNo(new UniqueIdCreator().getTrainUid()); // uuid
        ticket.setTrainUuid(check.trainNoFindCheck(Integer.parseInt(trainNo)).getId());
        ticket.setPrice(ticketPrice.getTicketPrice());
        ticket.setFromStop(fromStop);
        ticket.setToStop(toStop);
        ticket.setTakeDate(date);
        return ticket;
    }
}
