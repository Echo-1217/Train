package com.example.Train.service.valid;

import com.example.Train.controller.dto.request.TicketRequest;
import com.example.Train.model.StopRepo;
import com.example.Train.model.TrainRepo;
import com.example.Train.model.entity.Stop;
import com.example.Train.model.entity.Train;
import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.err.CheckException;
import com.example.Train.exception.err.TrainParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class TicketCreateCheck extends TrainBasicCheck {
    @Autowired
    TrainRepo trainRepo;
    @Autowired
    StopRepo stopRepo;


    public void ticketCreatedCheck(TicketRequest request) throws CheckException {
        List<CheckErrors> checkErrorsList = new ArrayList<>();
        Train train = trainNoFindCheck(Integer.parseInt(request.getTrain_no()));
        Optional<Stop> from = stopRepo.findByNameAndTrainId(request.getFrom_stop(), train.getId());
        Optional<Stop> to = stopRepo.findByNameAndTrainId(request.getTo_stop(), train.getId());
        // wrong No
        if (trainRepo.findByTrainNo(Integer.parseInt(request.getTrain_no())).isEmpty()) {
            checkErrorsList.add(new CheckErrors("TrainNoNotExists", "Train No does not exists"));
        }
        // same station
        if (request.getFrom_stop().equals(request.getTo_stop()) || from.isEmpty() || to.isEmpty()) {
            checkErrorsList.add(new CheckErrors("TicketStopsInvalid", "Ticket From & To is invalid"));
        }
        // wrong stop seq
        else if (from.get().getSeq() > to.get().getSeq()) {
            checkErrorsList.add(new CheckErrors("TicketStopsInvalid", "Ticket From & To is invalid"));
        }
        // train_No
        if (!checkErrorsList.isEmpty()) {
            throw new CheckException(checkErrorsList);
        }
    }


    public void dateValueCheck(String date) {
        // date
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            //setLenient用於設置Calendar是否寬鬆解析字符串，如果為false，則嚴格解析；默認為true，寬鬆解析
            format.setLenient(false);
            log.info("input date: " + format.parse(date));

        } catch (ParseException parseException) {
            throw new TrainParameterException("Pattern", "日期格式不正確 yyyy-mm-dd", "takeDate");
        }
    }

}
