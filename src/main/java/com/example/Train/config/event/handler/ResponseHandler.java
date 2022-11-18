//package com.example.Train.application;
//
//import com.example.Train.interfa.response.dto.StopDetail;
//import com.example.Train.interfa.response.dto.TrainDetail;
//import com.example.Train.interfa.response.dto.TrainResponse;
//import com.example.Train.interfa.event.customerErrorMsg.exception.CheckErrors;
//import com.example.Train.interfa.event.customerErrorMsg.exception.CustomizedException;
//import com.example.Train.domain.aggregate.entity.Train;
//import com.example.Train.infrastructure.repo.TrainRepo;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class ResponseHandler {
//    @Autowired
//    TrainRepo trainRepo;
//
//    public TrainResponse buildTrainResponse(int trainNo) throws Exception {
//        List<Map<String, ?>> dataList = trainRepo.findDataByTrainNo(trainNo);
//        List<StopDetail> stopDetails = new ArrayList<>();
//        if (dataList.isEmpty()) {
//            throw new Exception("車次不存在");
//        }
//        dataList.forEach(map ->
//                stopDetails.add(StopDetail.builder()
//                        .stop_name(map.get("name").toString())
//                        .stop_time(new SimpleDateFormat("HH:mm").format(map.get("time")))
//                        .build()));
//
//        return TrainResponse.builder()
//                .train_no(trainNo)
//                .train_kind(Train.NameKindTranslator.getName(dataList.get(0).get("TRAIN_KIND").toString()))
//                .stopDetails(stopDetails)
//                .build();
//    }
//
//    public List<TrainDetail> getStationDetailList(String via) throws CustomizedException {
//        List<TrainDetail> details = new ArrayList<>();
//        List<Map<String, ?>> dataList = trainRepo.findByVia(via.trim());
//        if (dataList.isEmpty()) {
//            throw new CustomizedException(List.of(new CheckErrors("viaNotExists", "停靠站不存在")));
//        }
//        dataList.forEach(m -> details.add(TrainDetail.builder()
//                .train_no((int) m.get("train_no"))
//                .train_kind(Train.NameKindTranslator.getName(m.get("train_kind").toString()))
//                .build()));
//        return details;
//    }
//
//
//}
