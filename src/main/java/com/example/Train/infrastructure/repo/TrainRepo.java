package com.example.Train.infrastructure.repo;

import com.example.Train.domain.aggregate.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TrainRepo extends JpaRepository<Train, String> {
    Optional<Train> findByTrainNo(Integer trainNo);

//    @Query(value = "select train_no,train_kind from TRAIN inner join TRAIN_STOP on TRAIN.UUID= TRAIN_STOP.train_uuid and TRAIN_STOP.name= ?1 ; ", nativeQuery = true)
//    List<Map<String, ?>> findByVia(String via);
//
//    @Query(value = "SELECT TRAIN_STOP.name,TRAIN_STOP.time,TRAIN.TRAIN_KIND FROM TRAIN inner join TRAIN_STOP  on TRAIN.UUID = TRAIN_STOP.train_uuid and TRAIN.TRAIN_NO = ?1  ;", nativeQuery = true)
//    List<Map<String, ?>> findDataByTrainNo(Integer trainNo);

//    @Query(value = "SELECT TRAIN_STOP.seq from TRAIN_STOP where TRAIN_STOP.name=?1 and TRAIN_STOP.train_uuid=?2 ", nativeQuery = true)
//    Optional<Map<String, ?>> findByNameAndTrainId(String name, String trainId);

}
