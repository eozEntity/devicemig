package com.notification.devicemig.service;

import com.notification.devicemig.date.Date;
import com.notification.devicemig.date.DateProvider;
import com.notification.devicemig.model.entity.CarIdBrand;
import com.notification.devicemig.repository.DevicemigRepository;
import com.notification.devicemig.request.VehicleWebClient;
import com.notification.devicemig.model.vo.DeviceVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceMigService {

    private final DevicemigRepository devicemigRepository;

    private final DateProvider dateProvider;

    private final DataLoaderService jsonDataLoaderService;

    //Json 파일 읽어서 처리
    public void verifyByJson() throws InterruptedException, ParseException, IOException {
        VehicleWebClient vehicleWebClient = new VehicleWebClient();
        List<DeviceVO> deviceMapping = jsonDataLoaderService.getData("GEN");
        List<String> shouldDeleteCarIds = new ArrayList<>();

        int loopCnt = 0;

        log.info("Total Count : {}", deviceMapping.size());

        for( DeviceVO device: deviceMapping ) {

            if( vehicleWebClient.request( device.getCarId(), device.getBrand() )
                               .isInvalidCarId() ){

                shouldDeleteCarIds.add( device.getCarId() );
            }

            if(shouldSleep(++loopCnt)){
                deleteAndCleanup(shouldDeleteCarIds);

            }
        }

//        devicemigRepository.delete( shouldDeleteCarIds );

    }

    private void deleteAndCleanup(List<String> shouldDeleteCarIds) throws InterruptedException {
        devicemigRepository.delete( shouldDeleteCarIds );

        System.out.println("===== Delete and Sleep ====");

        shouldDeleteCarIds.clear();

        Thread.sleep(250);
    }

    public void verifyByDate(){
        List<Date> dates = dateProvider.getDates();
        VehicleWebClient vehicleWebClient = new VehicleWebClient();

        for(Date date : dates){
            log.info(date.toString());
            //데이트로 가져오면
            //살아있는 carId를 계속 물어봐야한다.
            List<CarIdBrand> deviceMappings = devicemigRepository.getDeviceMappings(date);
            List<String> shouldDeleteCarIds = new ArrayList<>();

            for (CarIdBrand deviceMapping : deviceMappings) {

                if( vehicleWebClient.request( deviceMapping.getCarId(), deviceMapping.getBrand() )
                                    .isInvalidCarId() ){

                    shouldDeleteCarIds.add(deviceMapping.getCarId());   // 이걸 파일로 빼던가..
                }

            }

            // 하루에 대략 10,000건 삭제
            // devicemigRepository.delete( shouldDeleteCarIds );


        }
    }

    private static boolean shouldSleep(int cnt){
        return cnt % 10000 == 0;
    }
}

