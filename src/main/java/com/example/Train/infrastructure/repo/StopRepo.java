package com.example.Train.infrastructure.repo;

import com.example.Train.domain.aggregate.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StopRepo extends JpaRepository<Stop, String>, JpaSpecificationExecutor<Stop> {
    Optional<Stop> findByNameAndTrainId(@NonNull String name, @NonNull String trainId);

}
