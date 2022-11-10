package com.example.Train.model.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
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

    public Ticket(String tickNo, String trainUuid, String fromStop, String toStop, String takeDate, double price) {
        this.tickNo = tickNo;
        this.trainUuid = trainUuid;
        this.fromStop = fromStop;
        this.toStop = toStop;
        this.takeDate = takeDate;
        this.price = price;
    }
}
