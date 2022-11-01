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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRAIN_STOP")
public class TrainStop {
    @Id
    @Column(name = "UUID")
    private String uuid;
    @Column(name = "TRAIN_UUID")
    private String trainUuid;
    @Column(name = "SEQ")
    private int seq;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TIME")
    private LocalDateTime time;
    @Column(name = "DELETE_FLAG")
    private String deleteFlag;
}
