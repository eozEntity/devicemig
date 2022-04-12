package com.notification.devicemig;

import com.notification.devicemig.model.entity.DeviceMapping;
import com.notification.devicemig.repository.DevicemigRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class DeleteStressTest {

    @Autowired
    DevicemigRepository devicemigRepository;

    @MockBean
    private DevicemigApplication devicemigApplication;

    void init(){
//        url: jdbc:h2:mem:test
        int loopSize = 100000;
        for(int i=0;i<loopSize;i++){
            DeviceMapping deviceMapping = DeviceMapping.builder()
                                                    .deviceId(UUID.randomUUID().toString())
                                                    .vin("vin")
                                                    .carId("carId" + i)
                                                    .ccId(UUID.randomUUID().toString())
                                                    .nadId("nadid")
                                                    .build();

            devicemigRepository.save( deviceMapping );

        }
    }

    @Test
    @Disabled
    @DisplayName("100만건 Delete 수행 시간 테스트")
    void Delete_쿼리_수행시간_확인(){
        int loopSize = 1000000;

        long beforeTime = System.currentTimeMillis();

        for(int i=0;i<loopSize;i++){
            DeviceMapping deviceMapping = DeviceMapping.builder()
                    .deviceId(UUID.randomUUID().toString())
                    .vin("vin")
                    .carId("carId" + i)
                    .ccId(UUID.randomUUID().toString())
                    .nadId("nadid")
                    .build();

            devicemigRepository.delete( deviceMapping );
            //얘는 select문이 한번 더나감..
        }
        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        System.out.println("수행시간(m) : "+secDiffTime);
        //수행시간(m) : 133
    }

    @Test
    @Disabled
    @DisplayName("100만건 Delete in 수행 시간 테스트")
    void Delete_in_쿼리_수행시간_확인(){
        int loopSize = 100000;

        long beforeTime = System.currentTimeMillis();
        List<String> carIds = new ArrayList<>();

        for(int i=0;i<loopSize;i++){
            DeviceMapping deviceMapping = DeviceMapping.builder()
                    .deviceId(UUID.randomUUID().toString())
                    .vin("vin")
                    .carId("carId" + i)
                    .ccId(UUID.randomUUID().toString())
                    .nadId("nadid")
                    .build();

            carIds.add(deviceMapping.getCarId());

        }

        //얘는 select문이 안나감
        devicemigRepository.delete(carIds);

        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        System.out.println("수행시간(m) : "+secDiffTime);
//        수행시간(m) : 4

    }

}
