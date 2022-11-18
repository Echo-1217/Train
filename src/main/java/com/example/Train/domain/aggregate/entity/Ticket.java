package com.example.Train.domain.aggregate.entity;

import com.example.Train.config.event.exception.customerErrorMsg.CheckErrors;
import com.example.Train.config.event.exception.customerErrorMsg.CustomizedException;
import com.example.Train.config.event.exception.customerErrorMsg.ErrorInfo;
import com.example.Train.domain.command.AddTicketCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;

import javax.persistence.*;
import java.util.List;

@Getter
//@Setter
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

    public Ticket(AddTicketCommand command, String trainUuid, double price) throws CustomizedException {
        sameViaCheck(command);
        this.trainUuid = trainUuid;
        this.tickNo = RandomString.make(32).toUpperCase();
        this.price = price;
        this.fromStop = command.getFromStop();
        this.toStop = command.getToStop();
        this.takeDate = command.getTakeDate();
    }

    public void sameViaCheck(AddTicketCommand command) throws CustomizedException {
        if (command.getFromStop().equals(command.getToStop())) {
            throw new CustomizedException(List.of(new CheckErrors(ErrorInfo.ticketSameVia.getCode(), ErrorInfo.ticketSameVia.getErrorMessage())));
        }
    }
}
