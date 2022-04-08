package com.notification.devicemig.date;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
public class Date {
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

    private String begin;
    private String end;


    public static Date of( LocalDateTime date ){
        String beginDate = date.format(formatter) + "000000";    //20220408 + 000000;
        String endDate = date.plusDays(1).format(formatter) + "000000";            //20220409 + 000000;

        return Date.builder()
                .begin(beginDate)
                .end(endDate)
                .build();
    }
}
