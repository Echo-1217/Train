package com.example.Train.domain.domainService;

import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.err.CustomizedException;
import com.example.Train.exception.response.ErrorInfo;
import com.example.Train.domain.aggregate.valueObj.AddTicket;
import com.example.Train.domain.aggregate.entity.Stop;
import com.example.Train.domain.aggregate.entity.Train;
import com.example.Train.infraLayer.repository.StopRepo;
import com.example.Train.infraLayer.repository.TrainRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO: Q5
@Slf4j
@Component
public class TicketDomainService {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    StopRepo stopRepo;


    public void summaryCheck(AddTicket addTicket, Train train, CheckErrors... checkErrors) throws CustomizedException {
        List<CheckErrors> checkErrorsList = new ArrayList<>();

//        Train train = trainNoFindCheck(Integer.parseInt(addTicket.getTrainNo()));
//        Optional<Stop> from = stopRepo.findByNameAndTrainId(addTicket.getFromStop(), train.getId());
//        Optional<Stop> to = stopRepo.findByNameAndTrainId(addTicket.getToStop(), train.getId());
        // wrong No
//        if (trainRepo.findByTrainNo(Integer.parseInt(addTicket.getTrainNo())).isEmpty()) {
//            checkErrorsList.add(new CheckErrors("TrainNoNotExists", "Train No does not exists"));
//        }
//        trainNoCheck(addTicket,checkErrorsList);

//        // same station
//        if (addTicket.getFromStop().equals(addTicket.getToStop()) || from.isEmpty() || to.isEmpty()) {
//            checkErrorsList.add(new CheckErrors("TicketStopsInvalid", "Ticket From & To is invalid"));
//        }
        // wrong stop seq
//        else if (from.get().getSeq() > to.get().getSeq()) {
//            checkErrorsList.add(new CheckErrors("TicketStopsInvalid", "Ticket From & To is invalid"));
//        }
        viaCheck(addTicket, train, checkErrorsList);
        if (!checkErrorsList.isEmpty()) {
            throw new CustomizedException(checkErrorsList);
        }
    }


//    public void dateValueCheck(String date) {
//        // date
//        try {
//            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//            //setLenient用於設置Calendar是否寬鬆解析字符串，如果為false，則嚴格解析；默認為true，寬鬆解析
//            format.setLenient(false);
//            log.info("input date: " + format.parse(date));
//
//        } catch (ParseException parseException) {
//            throw new TrainParameterException("Pattern", "日期格式不正確 yyyy-mm-dd", "takeDate");
//        }
//    }


    public Train trainNoCheckAndGet(AddTicket addTicket) throws CustomizedException {
        // train_No
        Optional<Train> train = trainRepo.findByTrainNo(Integer.parseInt(addTicket.getTrainNo()));
        if (train.isEmpty()) {
            log.info("車次不存在");
            throw new CustomizedException(List.of(new CheckErrors(ErrorInfo.trainNoNotExists.getCode(), ErrorInfo.trainNoNotExists.getErrorMessage())));
        }
        return train.get();
    }

    public void viaCheck(AddTicket addTicket, Train train, List<CheckErrors> checkErrorsList) {

        Optional<Stop> from = stopRepo.findByNameAndTrainId(addTicket.getFromStop(), train.getId());
        Optional<Stop> to = stopRepo.findByNameAndTrainId(addTicket.getToStop(), train.getId());

        // check exist
        if (from.isEmpty() || to.isEmpty()) {
            log.info("停靠站不存在");
            checkErrorsList.add(new CheckErrors(ErrorInfo.ticketStopsNotExist.getCode(), ErrorInfo.ticketStopsNotExist.getErrorMessage()));
        }
        // check seq
        else if (from.get().getSeq() > to.get().getSeq()) {
            log.info("wrong seq");
            checkErrorsList.add(new CheckErrors(ErrorInfo.ticketStopsInvalid.getCode(), ErrorInfo.ticketStopsInvalid.getErrorMessage()));
        }
    }

    public void trainNoCheck(AddTicket addTicket, List<CheckErrors> checkErrorsList) {
        // train_No
        Optional<Train> train = trainRepo.findByTrainNo(Integer.parseInt(addTicket.getTrainNo()));
        if (train.isEmpty()) {
            checkErrorsList.add(new CheckErrors(ErrorInfo.trainNoNotExists.getCode(), ErrorInfo.trainNoNotExists.getErrorMessage()));
        }
    }
}
