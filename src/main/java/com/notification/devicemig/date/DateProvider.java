package com.notification.devicemig.date;

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

    @PostConstruct
    public void init(){
        //for test
//        LocalDateTime oldestDate = LocalDateTime.now().minusDays(7);
        LocalDateTime oldestDate = devicemigRepository.getOldestDate();

        long diff = DAYS.between( oldestDate, LocalDateTime.now() );

        for(long i=0 ; i<=diff ; i++){
            LocalDateTime addedDate = oldestDate.plusDays(i);

            dates.add( Date.of(addedDate) );
        }
    }

}
