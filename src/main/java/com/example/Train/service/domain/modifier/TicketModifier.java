package com.example.Train.service.domain.modifier;

import com.example.Train.exception.err.CustomizedException;
import com.example.Train.model.entity.Ticket;
import com.example.Train.service.domain.orther.UniqueIdCreator;
import com.example.Train.service.domain.orther.api.TicketApi;
import com.example.Train.service.domain.valid.TicketDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TicketModifier {
    @Autowired
    TicketApi ticketApi;
    @Autowired
    TicketDomainService ticketDomainService;

    public Ticket buildTicket(String trainNo, String fromStop, String toStop, String date) throws CustomizedException {
//        Ticket ticket= new Ticket()
//        ticket.setTickNo(new UniqueIdCreator().getTrainUid()); // uuid
//        ticket.setTrainUuid(ticketDomainService.trainNoFindCheck(Integer.parseInt(trainNo)).getId());
//        ticket.setPrice(ticketApi.getTicketPrice());
//        ticket.setFromStop(fromStop);
//        ticket.setToStop(toStop);
//        ticket.setTakeDate(date);
//        return ticket;
        //======================================
        return new Ticket(ticketDomainService.trainNoFindCheck(Integer.parseInt(trainNo)).getId(), new UniqueIdCreator().getTrainUid(), fromStop, toStop, date, ticketApi.getTicketPrice());
//==============================
//        return Ticket.builder()
//                .trainUuid(ticketDomainService.trainNoFindCheck(Integer.parseInt(trainNo)).getId())
//                .tickNo(new UniqueIdCreator().getTrainUid())
//                .price(ticketApi.getTicketPrice())
//                .fromStop(fromStop)
//                .toStop(toStop)
//                .takeDate(date)
//                .build();
    }
}
