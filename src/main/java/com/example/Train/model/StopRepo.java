package com.example.Train.model;

import com.example.Train.model.entity.Stop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StopRepo extends JpaRepository<Stop, String> {
    List<Stop> findByTrainId(String trainId);

    List<Stop> findByName(@NonNull String via);

    Optional<Stop> findByNameAndTrainId(@NonNull String name, @NonNull String trainId);

}
