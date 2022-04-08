package com.notification.devicemig.request;

import com.notification.devicemig.entity.DeviceMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class VehicleClient {

    private static WebClient webClientH = WebClient.builder().build();
    private static WebClient webClientK = WebClient.builder().build();
    private static WebClient webClientG = WebClient.builder().build();

    private static String hUrl = "";
    private static String kUrl = "";
    private static String gUrl = "";

    private static String hToken = "";
    private static String kToken = "";
    private static String gToken = "";

    public static int request(DeviceMapping deviceMapping) {

        return deviceMapping.getCarId().equals("c_3")?200:400;
    }

//    public static int request(DeviceMapping deviceMapping){
//        Mono<ResponseEntity<String>> response = null;
//
//        String brand = deviceMapping.getBrand();
//        String carId = deviceMapping.getCarId();
//
//        if("BLU".equals(brand)){
//            response = webClientH.get().uri(hUrl, carId)
//                    .header("authorization", "Basic ....")
//                    .header("Content-Type", "application/json")
//                    .exchange()
//                    .flatMap( res -> res.toEntity(String.class) );
//
//        }else if("UVO".equals(brand)){
//            response = webClientK.get().uri(kUrl, carId)
//                    .header("authorization", "Basic ....")
//                    .header("Content-Type", "application/json")
//                    .exchange()
//                    .flatMap( res -> res.toEntity(String.class) );
//
//        }else if("GEN".equals(brand)){
//            response = webClientG.get().uri(gUrl, carId)
//                    .header("authorization", "Basic ....")
//                    .header("Content-Type", "application/json")
//                    .exchange()
//                    .flatMap( res -> res.toEntity(String.class) );
//        }
//
//        return response.block()
//                    .getStatusCodeValue();
//
//    }




}
