package com.notification.devicemig.date;

import com.notification.devicemig.DevicemigApplication;
import com.notification.devicemig.model.entity.DeviceMapping;
import com.notification.devicemig.exception.DateNotFoundException;
import com.notification.devicemig.repository.DevicemigRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
@Rollback(true)
class DateProviderTest {

    @Autowired
    private DevicemigRepository devicemigRepository;

    @Autowired
    private DateProvider dateProvider;

    @MockBean
    private DevicemigApplication devicemigApplication;


    //통합 테스트하면 다른 테스트에서 insert한 데이터들이 남아있다.
    //왜 남아있을까. test 환경에서는 rollback 되는데..
    //그래서 @AfterEach로 delete 해줬다. 정답은 아닌듯..
    @Test
    @DisplayName("가장 오래된 날부터 오늘까지 날짜리스트를 가져온다.")
    void 가장오래된_날부터_오늘까지_날짜리스트_확인(){
        //given
        LocalDateTime oldestDate = LocalDateTime.of(2022, 1,1,11,0);

        int expectedDiff = (int) DAYS.between( oldestDate, LocalDateTime.now() ); //오늘날짜 포함

        DeviceMapping deviceMapping = DeviceMapping.builder()
                                                .brand("GC")
                                                .deviceId("deviceId3")
                                                .carId("carId3")
                                                .nadId("nadId4")
                                                .ccId(UUID.randomUUID().toString()+"_GEN")
                                                .vin("vin4")
                                                .createdAt(oldestDate)
                                                .updatedAt(LocalDateTime.now())
                                                .build();

        devicemigRepository.save( deviceMapping );

        //when
        List<Date> dates = dateProvider.getDates();

        //then
        assertThat(dates).isNotNull();
        assertThat(dates.size()).isEqualTo(expectedDiff+1);
    }

    @Test
    @DisplayName("오래된 날짜를 가져오지 못하면 DataNotFoundException 발생한다.")
    void DateNotFoundException_발생_확인(){
        assertThrows(DateNotFoundException.class, ()->{
            List<Date> dates = dateProvider.getDates();
        });
    }

}