package com.example.Train.intfa.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    @NotEmpty(message = "車次不可以為空")
    @JsonProperty("train_no")
    private String trainNo;
    @NotBlank(message = "起站不可以為空")
    @JsonProperty("from_stop")
    private String fromStop;
    @NotBlank(message = "到站不可以為空")
    @JsonProperty("to_stop")
    private String toStop;
    //    @Pattern(regexp = "^\\d{4}[\\-/\\.](0?[1-9]|1[012])[\\-/\\.](0?[1-9]|[12][0-9]|3[01])$", message = "日期格式不正確 yyyy-mm-dd")
    @Pattern(regexp = "((((19|20)\\d{2})-(0?[13578]|1[02])-(0?[1-9]|[12]\\d|3[01]))|(((19|20)\\d{2})-(0?[469]|11)-(0?[1-9]|[12]\\d|30))|(((19|20)\\d{2})-0?2-(0?[1-9]|1\\d|2[0-8]))|((((19|20)([13579][26]|[2468][048]|0[48]))|(2000))-0?2-(0?[1-9]|[12]\\d)))$", message = "日期格式不正確 yyyy-mm-dd")
    @JsonProperty("take_date")
    private String takeDate;

    //  ^代表開始的匹配。
    //  $代表結尾的匹配。
    //  \\d代表匹配數字，同等於[0-9]。
    //  \\d後接的大括弧{4}中的數字代表前面符號要出現的次數，所以//d{4}代表可出現4次數字，例如2017或1996都匹配。
    //  中括弧[...]代表之中的任一，所以[\\-/\\.]代表-，/或.任一都匹配。
    //  ?前面的符號可出現一次或不出現，所以0?代表0可以出現或不出現。
}
