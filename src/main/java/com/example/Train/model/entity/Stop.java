package com.example.Train.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRAIN_STOP")
public class Stop {
    @Id
    @Column(name = "UUID")
    private String id;
    @Column(name = "TRAIN_UUID")
    private String trainId;
    @Column(name = "SEQ")
    private int seq;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TIME")
    private LocalTime time;
    @Column(name = "DELETE_FLAG")
    private String deleteFlag;


}
