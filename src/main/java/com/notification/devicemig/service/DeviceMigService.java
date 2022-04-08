package com.notification.devicemig.service;

import com.notification.devicemig.date.Date;
import com.notification.devicemig.date.DateProvider;
import com.notification.devicemig.repository.DevicemigRepository;
import com.notification.devicemig.request.VehicleClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceMigService {

    private final DevicemigRepository devicemigRepository;

    private final DateProvider dateProvider;

    public void verify() throws InterruptedException {
        // 작업대상 날짜 가져옴
        List<Date> dates = dateProvider.getDates();

        for(Date date : dates){


            //1번 2번으로 보낼 수 있다면?
            devicemigRepository.getDeviceMappings(date.getBegin(), date.getEnd())
                                .stream()
                                .filter( dm -> dm.getCcId() != null && dm.getCcId().length() >= 2 )
                                .forEach( deviceMapping -> {
                                        int response = VehicleClient.request(deviceMapping);

                                        if( invalidCarId(response) ){
                                            // log 파일에 쌓아서 돌려보는게 나을듯
                                            //
                                            System.out.println(deviceMapping.getCarId());
//                                            devicemigRepository.delete( deviceMapping );
                                        }
                                } );

            // for문 + if문 + sleep + count -> 로그 쌓기
            // distinct car_id 로 많이 줄어들긴 함

        }

        //쉬는시간
        Thread.sleep(50);

    }
    private static boolean invalidCarId(int statusCode){
        return statusCode != 200;
    }


}

