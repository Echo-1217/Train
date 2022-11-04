package com.example.Train.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "TRAIN")
public class Train {
    @Id
    @Column(name = "UUID")
    private String id;
    @Column(name = "TRAIN_NO")
    private int trainNo;
    @Column(name = "TRAIN_KIND")
    private String trainKind;
}