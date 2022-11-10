package com.example.Train.model.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//@Setter
//@Getter
@Data
@Entity
@Builder
@AllArgsConstructor
@Table(name = "TRAIN")
@NoArgsConstructor
public class Train {
    @Id
    @Column(name = "UUID")
    private String id;
    @Column(name = "TRAIN_NO")
    private int trainNo;
    @Column(name = "TRAIN_KIND")
    private String trainKind;
}