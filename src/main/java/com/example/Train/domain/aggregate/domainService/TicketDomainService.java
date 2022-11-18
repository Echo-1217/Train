package com.example.Train.domain.aggregate.domainService;

import com.example.Train.config.event.exception.customerErrorMsg.CheckErrors;
import com.example.Train.config.event.exception.customerErrorMsg.CustomizedException;
import com.example.Train.config.event.exception.customerErrorMsg.ErrorInfo;
import com.example.Train.domain.aggregate.entity.Train;
import com.example.Train.domain.command.AddTicketCommand;
import com.example.Train.infrastructure.repo.TrainRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

// TODO: Q5
@Slf4j
@Component
public class TicketDomainService {
    @Autowired
    TrainRepo trainRepo;

    public String stopCheckAndGetTrainId(AddTicketCommand command) throws CustomizedException {
        List<CheckErrors> checkErrorsList = new ArrayList<>();
        AtomicInteger from = new AtomicInteger(-1);
        AtomicInteger to = new AtomicInteger(-1);

        Optional<Train> opTrain = trainRepo.findByTrainNo(Integer.parseInt(command.getTrainNo()));
        if (opTrain.isEmpty()) {
            log.info("車次不存在");
            checkErrorsList.add(new CheckErrors(ErrorInfo.trainNoNotExists.getCode(), ErrorInfo.trainNoNotExists.getErrorMessage()));
            throw new CustomizedException(checkErrorsList);
        }
        opTrain.ifPresent(value -> value.getStopList().forEach(stop -> {
            if (command.getFromStop().equals(stop.getName())) {
                from.set(stop.getSeq());
            }
            if (command.getToStop().equals(stop.getName())) {
                to.set(stop.getSeq());
            }
        }));
        if (from.get() < 1 || to.get() < 1) {
            log.info("停靠站不存在");
            checkErrorsList.add(new CheckErrors(ErrorInfo.ticketStopsNotExist.getCode(), ErrorInfo.ticketStopsNotExist.getErrorMessage()));
        }
        if (from.get() > 1 && to.get() > 1 && from.get() > to.get()) {
            log.info("wrong seq");
            checkErrorsList.add(new CheckErrors(ErrorInfo.ticketStopsInvalid.getCode(), ErrorInfo.ticketStopsInvalid.getErrorMessage()));
        }
        if (!checkErrorsList.isEmpty()) {
            throw new CustomizedException(checkErrorsList);
        }
        return opTrain.get().getId();
    }

//    public void summaryCheck(AddTicketCommand command, CheckErrors... checkErrors) throws CustomizedException {
//        List<CheckErrors> checkErrorsList = new ArrayList<>(Arrays.stream(checkErrors).filter(Objects::nonNull).toList());
//
////        Train train = trainNoFindCheck(Integer.parseInt(command.getTrainNo()));
////        Optional<Stop> from = stopRepo.findByNameAndTrainId(command.getFromStop(), train.getId());
////        Optional<Stop> to = stopRepo.findByNameAndTrainId(command.getToStop(), train.getId());
//        // wrong No
////        if (trainRepo.findByTrainNo(Integer.parseInt(command.getTrainNo())).isEmpty()) {
////            checkErrorsList.add(new CheckErrors("TrainNoNotExists", "Train No does not exists"));
////        }
////        trainNoCheck(command,checkErrorsList);
//
////        // same station
////        if (command.getFromStop().equals(command.getToStop()) || from.isEmpty() || to.isEmpty()) {
////            checkErrorsList.add(new CheckErrors("TicketStopsInvalid", "Ticket From & To is invalid"));
////        }
//        // wrong stop seq
////        else if (from.get().getSeq() > to.get().getSeq()) {
////            checkErrorsList.add(new CheckErrors("TicketStopsInvalid", "Ticket From & To is invalid"));
////        }
////        viaCheck(command, train, checkErrorsList);
////        stopCheck(command,checkErrorsList);
//        if (!checkErrorsList.isEmpty()) {
//            throw new CustomizedException(checkErrorsList);
//        }
//    }


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


//    public Train trainNoCheckAndGet(AddTicketCommand addTicketCommand) throws CustomizedException {
//        // train_No
//        Optional<Train> train = trainRepo.findByTrainNo(Integer.parseInt(addTicketCommand.getTrainNo()));
//        if (train.isEmpty()) {
//            log.info("車次不存在");
//            throw new CustomizedException(List.of(new CheckErrors(ErrorInfo.trainNoNotExists.getCode(), ErrorInfo.trainNoNotExists.getErrorMessage())));
//        }
//        return train.get();
//    }

//    public void viaCheck(AddTicketCommand addTicketCommand, Train train, List<CheckErrors> checkErrorsList) {
//        int from, to;
////        Optional<Stop> from = stopRepo.findByNameAndTrainId(addTicketCommand.getFromStop(), train.getId());
////        Optional<Stop> to = stopRepo.findByNameAndTrainId(addTicketCommand.getToStop(), train.getId());
//        Optional<Map<String, ?>> opFrom = trainRepo.findByNameAndTrainId(addTicketCommand.getFromStop(), train.getId());
//        Optional<Map<String, ?>> opTo = trainRepo.findByNameAndTrainId(addTicketCommand.getToStop(), train.getId());
//
//
//        if (opFrom.isEmpty() || opTo.isEmpty() || opFrom.get().isEmpty() || opTo.get().isEmpty()) {
//            log.info("stop不存在");
//            checkErrorsList.add(new CheckErrors(ErrorInfo.ticketStopsNotExist.getCode(), ErrorInfo.ticketStopsNotExist.getErrorMessage()));
//            return;
//        }
//        String seq = "SEQ";
//        from = Integer.parseInt(opFrom.get().get(seq).toString());
//        to = Integer.parseInt(opTo.get().get(seq).toString());
//
//
//        // check exist
////        if (from.isEmpty() || to.isEmpty()) {
////            log.info("停靠站不存在");
////            checkErrorsList.add(new CheckErrors(ErrorInfo.ticketStopsNotExist.getCode(), ErrorInfo.ticketStopsNotExist.getErrorMessage()));
////        }
//
////        if (from == 0 || to == 0) {
////            log.info("停靠站不存在");
////            checkErrorsList.add(new CheckErrors(ErrorInfo.ticketStopsNotExist.getCode(), ErrorInfo.ticketStopsNotExist.getErrorMessage()));
////        }
//        // check seq
////        else if (from.get().getSeq() > to.get().getSeq()) {
////            log.info("wrong seq");
////            checkErrorsList.add(new CheckErrors(ErrorInfo.ticketStopsInvalid.getCode(), ErrorInfo.ticketStopsInvalid.getErrorMessage()));
////        }
//        if (from > to) {
//            log.info("wrong seq");
//            checkErrorsList.add(new CheckErrors(ErrorInfo.ticketStopsInvalid.getCode(), ErrorInfo.ticketStopsInvalid.getErrorMessage()));
//        }
//    }

//    public void trainNoCheck(AddTicketCommand addTicketCommand, List<CheckErrors> checkErrorsList) {
//        // train_No
//        Optional<Train> train = trainRepo.findByTrainNo(Integer.parseInt(addTicketCommand.getTrainNo()));
//        if (train.isEmpty()) {
//            checkErrorsList.add(new CheckErrors(ErrorInfo.trainNoNotExists.getCode(), ErrorInfo.trainNoNotExists.getErrorMessage()));
//        }
//    }


}
