package com.example.Train.model;

import com.example.Train.model.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepo extends JpaRepository<Train, String> {
    Optional<Train> findByTrainNo(Integer trainNo);
}
