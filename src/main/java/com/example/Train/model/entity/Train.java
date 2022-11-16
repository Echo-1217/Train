package com.example.Train.model.entity;

import com.example.Train.exception.err.CheckErrors;
import com.example.Train.exception.response.ErrorInfo;
import com.example.Train.model.addObj.AddTrain;
import lombok.*;
import net.bytebuddy.utility.RandomString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
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

    public Train(AddTrain add) {
        this.id = RandomString.make(32).toUpperCase();
        this.trainNo = add.getTrainNo();
        this.trainKind = NameKindTranslator.getKind(add.getTrainKind());
    }


    // TODO: Q7
    public CheckErrors checkKind(AddTrain addTrain) {

        // invalid kind
        if (null == NameKindTranslator.getKind(addTrain.getTrainKind())) {
            return new CheckErrors(ErrorInfo.trainKindInvalid.getCode(), ErrorInfo.trainKindInvalid.getErrorMessage());
        }
        return null;
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
}