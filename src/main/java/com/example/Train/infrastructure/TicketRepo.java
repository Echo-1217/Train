package com.example.Train.infrastructure;

import com.example.Train.domain.aggregate.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends JpaRepository<Ticket, String> {
}

