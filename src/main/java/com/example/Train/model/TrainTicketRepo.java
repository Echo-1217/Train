package com.example.Train.model;

import com.example.Train.model.entity.TrainTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainTicketRepo extends JpaRepository<TrainTicket, String> {
}

