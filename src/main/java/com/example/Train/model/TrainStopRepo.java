package com.example.Train.model;

import com.example.Train.model.entity.TrainStop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainStopRepo extends JpaRepository<TrainStop, String> {
    List<TrainStop> findByTrainUuid(String trainId);
}
