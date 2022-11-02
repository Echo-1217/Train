package com.example.Train.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRAIN_TICKET")
public class Ticket {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "TRAIN_NO")
    private String trainNo;
    @Column(name = "TRAIN_UUID")
    private String trainUuid;
    @Column(name = "FROM_STOP")
    private String fromStop;
    @Column(name = "TO_STOP")
    private String toStop;
    @Column(name = "TAKE_DATE")
    private LocalDateTime takeDate;
    @Column(name = "PRICE")
    private double price;
}
