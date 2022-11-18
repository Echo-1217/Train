package com.example.Train.domain.aggregate.entity;

import com.example.Train.config.event.exception.customerErrorMsg.CheckErrors;
import com.example.Train.config.event.exception.customerErrorMsg.CustomizedException;
import com.example.Train.config.event.exception.customerErrorMsg.ErrorInfo;
import com.example.Train.domain.command.AddTrainCommand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.bytebuddy.utility.RandomString;

import javax.persistence.*;
import java.util.List;

@Getter
//@Setter
@Entity
@AllArgsConstructor
@Table(name = "TRAIN")
@NoArgsConstructor
public class Train {
    @Id
    @Column(name = "UUID")
    private String id;
    @Column(name = "TRAIN_NO")
    private int trainNo;
    @Column(name = "TRAIN_KIND")
    private String trainKind;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trainId", cascade = CascadeType.ALL)
    private List<Stop> stopList;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "id", cascade = CascadeType.ALL)
//    private  List<Ticket> ticketList;


    public Train(AddTrainCommand command) throws CustomizedException {
//        List<CheckErrors> checkErrorsList = new ArrayList<>(List.of(checkKind(command)));
        checkKind(command);
        this.id = RandomString.make(32).toUpperCase();
        this.trainNo = command.getTrainNo();
        this.trainKind = NameKindTranslator.getKind(command.getTrainKind());
        this.stopList = new Stop().buildList(command, this.id);
//        this.ticketList = List.of(new Ticket(command,this.id,new TicketOutBoundService().getTicketPrice()))
    }


    // TODO: Q7
    public void checkKind(AddTrainCommand addTrainCommand) throws CustomizedException {

        // invalid kind
        if (null == NameKindTranslator.getKind(addTrainCommand.getTrainKind())) {
            throw new CustomizedException(List.of(new CheckErrors(ErrorInfo.trainKindInvalid.getCode(), ErrorInfo.trainKindInvalid.getErrorMessage())));
        }
//        return null;
    }

    public enum NameKindTranslator {

        A("諾亞方舟號", "A"), B("霍格華茲號", "B");


        String name;
        String kind;

        NameKindTranslator(String name, String kind) {
            this.name = name;
            this.kind = kind;
        }

        public static String getName(String kind) {
            for (NameKindTranslator n : NameKindTranslator.values()) {
                if (n.getKind().equals(kind)) {
                    return n.getName();
                }
            }
            return null;
        }

        public static String getKind(String name) {
            for (NameKindTranslator n : NameKindTranslator.values()) {
                if (n.getName().equals(name)) {
                    return n.getKind();
                }
            }
            return null;
        }


        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKind() {
            return kind;
        }

        public void setKind(String kind) {
            this.kind = kind;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

//    private void checkSummary(){
//
//    }
}