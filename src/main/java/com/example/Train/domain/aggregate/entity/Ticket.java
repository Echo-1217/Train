package com.example.Train.domain.entity;

import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.response.ErrorInfo;
import com.example.Train.domain.valueObj.AddTicket;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

import javax.persistence.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Slf4j
@Table(name = "TRAIN_TICKET")
public class Ticket {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ticket_no")
    private String tickNo;
    @Column(name = "TRAIN_UUID")
    private String trainUuid;
    @Column(name = "FROM_STOP")
    private String fromStop;
    @Column(name = "TO_STOP")
    private String toStop;
    @Column(name = "TAKE_DATE")
    private String takeDate;
    @Column(name = "PRICE")
    private double price;

    public Ticket(AddTicket addTicket, Train train, double price) {
        this.trainUuid = train.getId();
        this.tickNo = RandomString.make(32).toUpperCase();
        this.price = price;
        this.fromStop = addTicket.getFromStop();
        this.toStop = addTicket.getToStop();
        this.takeDate = addTicket.getTakeDate();
    }

    public CheckErrors sameViaCheck(AddTicket addTicket) {
        if (addTicket.getFromStop().equals(addTicket.getToStop())) {
            return new CheckErrors(ErrorInfo.ticketSameVia.getCode(), ErrorInfo.ticketSameVia.getErrorMessage());
        }
        return null;
    }
}
