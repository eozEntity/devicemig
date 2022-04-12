package com.notification.devicemig.date;

import com.notification.devicemig.exception.DateNotFoundException;
import com.notification.devicemig.repository.DevicemigRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
@Getter
@RequiredArgsConstructor
public class DateProvider {

    private final DevicemigRepository devicemigRepository;

    private List<Date> dates = new ArrayList<>();

    public List<Date> getDates(){
        LocalDateTime oldestDate = devicemigRepository.getOldestDate();
        if(oldestDate == null)
            throw new DateNotFoundException("DEVICE_MAPPING 에서 가장 오래된 날짜를 가져오지 못했습니다.");

        long diff = DAYS.between( oldestDate, LocalDateTime.now() );

        for(long i=0 ; i<=diff ; i++){
            LocalDateTime addedDate = oldestDate.plusDays(i);

            dates.add( Date.of(addedDate) );
        }

        return dates;
    }
}
