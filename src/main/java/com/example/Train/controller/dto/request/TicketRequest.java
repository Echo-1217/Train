package com.example.Train.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    @NotBlank(message = "車次不可以為空")
    private String train_no;
    @NotBlank(message = "起站不可以為空")
    private String from_stop;
    @NotBlank(message = "到站不可以為空")
    private String to_stop;
    @Pattern(regexp = "^\\d{4}[\\-/\\.](0?[1-9]|1[012])[\\-/\\.](0?[1-9]|[12][0-9]|3[01])$", message = "日期格式不正確 yyyy-mm-dd")
    private String take_date;

    //  ^代表開始的匹配。
    //  $代表結尾的匹配。
    //  \\d代表匹配數字，同等於[0-9]。
    //  \\d後接的大括弧{4}中的數字代表前面符號要出現的次數，所以//d{4}代表可出現4次數字，例如2017或1996都匹配。
    //  中括弧[...]代表之中的任一，所以[\\-/\\.]代表-，/或.任一都匹配。
    //  ?前面的符號可出現一次或不出現，所以0?代表0可以出現或不出現。
}
