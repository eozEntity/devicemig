package com.notification.devicemig.date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DateTest {

    @Test
    @DisplayName("Date 객체 속성은 end, start 이다.")
    void Date_객체_속성_확인(){
        //given, when
        Date date = Date.of( LocalDateTime.of(2022, 1,1,11,0) );

        //then
        assertThat(date.getBegin()).isEqualTo("20220101000000");
        assertThat(date.getEnd()).isEqualTo("20220102000000");

    }

    @Test
    @DisplayName("Date 객체 end, start 속성 포멧은 yyyymmdd+000000 이다.")
    void Date_객체_속성_포멧_확인(){
        //given, when
        Date date = Date.of( LocalDateTime.of(2022, 1,1,11,0) );

        //then
        assertThat(date.getBegin().length()).isEqualTo(14);
        assertThat(date.getEnd().length()).isEqualTo(14);

        assertThat(date.getBegin()).endsWith("000000");
        assertThat(date.getEnd()).endsWith("000000");

    }

}