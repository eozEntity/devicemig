package com.notification.devicemig.repository;


import com.notification.devicemig.DevicemigApplication;
import com.notification.devicemig.date.Date;
import com.notification.devicemig.model.entity.CarIdBrand;
import com.notification.devicemig.model.entity.DeviceMapping;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DevicemigRepositoryTest {

    @Autowired
    private DevicemigRepository devicemigRepository;

    @MockBean
    private DevicemigApplication devicemigApplication;

    private LocalDateTime oldestDate = LocalDateTime.of(2022, 1,21,11,0);
    private LocalDateTime testDate1 = LocalDateTime.of(2022, 4,9,14,0);
    private LocalDateTime testDate2 = LocalDateTime.of(2022, 4,9,21,0);

    @AfterEach
    void cleanUp(){
        devicemigRepository.deleteAll();
    }


    @Test
    @DisplayName("기준날짜로 DeviceMapping 엔티티를 가져온다.")
    void 기준날짜로_DeviceMapping_엔티티를_가져온다(){
        //given
        initTestData();
        Date date = Date.of( testDate1 );
        int expectedCount = 2;

        //when
        List<CarIdBrand> deviceMappings = devicemigRepository.getDeviceMappings(date);

        //then
        assertThat(deviceMappings.size()).isEqualTo( expectedCount );
    }

    @Test
    @DisplayName("중복 제거된 DeviceMapping을 가져온다.")
    void 중복_제거_확인(){
        //given
        Date date = Date.of(testDate1);
        DeviceMapping duplicatedDevice1 = DeviceMapping.builder()
                                            .brand("GC")
                                            .deviceId("deviceId3")
                                            .carId("carId3")
                                            .nadId("nadId4")
                                            .ccId(UUID.randomUUID().toString()+"_GEN")
                                            .vin("vin4")
                                            .createdAt(testDate1)
                                            .updatedAt(LocalDateTime.now())
                                            .build();

        DeviceMapping duplicatedDevice2 = DeviceMapping.builder()
                                            .brand("GC")
                                            .deviceId("deviceId4")
                                            .carId("carId3")
                                            .nadId("nadId4")
                                            .ccId(UUID.randomUUID().toString()+"_GEN")
                                            .vin("vin4")
                                            .createdAt(testDate1)
                                            .updatedAt(LocalDateTime.now())
                                            .build();

        DeviceMapping deviceMapping = DeviceMapping.builder()
                                            .brand("GC")
                                            .deviceId("deviceId1")
                                            .carId("carId1")
                                            .nadId("nadId1")
                                            .ccId(UUID.randomUUID().toString()+"_GEN")
                                            .vin("vin1")
                                            .createdAt(testDate1)
                                            .updatedAt(LocalDateTime.now())
                                            .build();

        int expectedCount = 2;

        //when
        devicemigRepository.save(duplicatedDevice1);
        devicemigRepository.save(duplicatedDevice2);
        devicemigRepository.save(deviceMapping);

        List<CarIdBrand> deviceMappings = devicemigRepository.getDeviceMappings(date);

        //then
        assertThat(deviceMappings.size()).isEqualTo( expectedCount );
    }


    @Test
    @DisplayName("DeviceMapping 엔티티로 DEVICE_MAPPING 테이블 데이터를 삭제한다.")
    void DEVICE_MAPPING_테이블_삭제확인_엔티티(){
        //given
        initTestData();
        String deviceId = "deviceId1";
        int expectedCount = (int) devicemigRepository.count();

        DeviceMapping deviceMapping = devicemigRepository.findById(deviceId).get();

        //when
        devicemigRepository.delete( deviceMapping );
        int actualCount = (int) devicemigRepository.count();

        //then
        assertThat(actualCount).isEqualTo(expectedCount-1);
    }

    @Test
    @DisplayName("CarId로 DEVICE_MAPPING 테이블 데이터를 삭제한다.")
    void DEVICE_MAPPING_테이블_삭제확인_CarId(){
        //given
        initTestData();
        List<String> carIds = Arrays.asList("carId1");
        int expectedCount = 1;

        //when
        devicemigRepository.delete( carIds );

        //then
        int actualCount = (int) devicemigRepository.count();

        assertThat(actualCount).isEqualTo(expectedCount);
    }

    @Test
    @DisplayName("DEVICE_MAPPING 테이블에서 가장 오래된 날짜를 가져온다.")
    void 오래된_날짜_확인(){
        //given
        initTestData();

        //when
        LocalDateTime actualDate = devicemigRepository.getOldestDate();

        //then
        assertThat(actualDate).isNotNull();
        assertThat(actualDate.getYear()).isEqualTo(oldestDate.getYear());
        assertThat(actualDate.getMonth()).isEqualTo(oldestDate.getMonth());
        assertThat(actualDate.getDayOfMonth()).isEqualTo(oldestDate.getDayOfMonth());
    }

    void initTestData(){
        DeviceMapping deviceMapping1 = DeviceMapping.builder()
                                                .brand("BL")
                                                .deviceId("deviceId1")
                                                .carId("carId1")
                                                .nadId("nadId1")
                                                .ccId(UUID.randomUUID().toString()+"_UVO")
                                                .vin("vin1")
                                                .createdAt(oldestDate)
                                                .updatedAt(LocalDateTime.now())
                                                .build();

        DeviceMapping deviceMapping2 = DeviceMapping.builder()
                                                .brand("BL")
                                                .deviceId("deviceId2")
                                                .carId("carId2")
                                                .nadId("nadId2")
                                                .ccId(UUID.randomUUID().toString()+"_BLU")
                                                .vin("vin2")
                                                .createdAt(testDate1)
                                                .updatedAt(LocalDateTime.now())
                                                .build();

        DeviceMapping deviceMapping3 = DeviceMapping.builder()
                                                .brand("BL")
                                                .deviceId("deviceId3")
                                                .carId("carId1")
                                                .nadId("nadId1")
                                                .ccId(UUID.randomUUID().toString()+"_GEN")
                                                .vin("vin1")
                                                .createdAt(testDate2)
                                                .updatedAt(LocalDateTime.now())
                                                .build();


        devicemigRepository.save(deviceMapping1);
        devicemigRepository.save(deviceMapping2);
        devicemigRepository.save(deviceMapping3);
    }




}