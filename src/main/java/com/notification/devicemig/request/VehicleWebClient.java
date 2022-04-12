package com.notification.devicemig.request;

import com.notification.devicemig.model.entity.CarIdBrand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.*;

@Component
public class VehicleWebClient {

    private Mono<ResponseEntity<String>> responseEntityMono;

    private static final String BLUELINK_BASIC_TOKEN    = "";
    private static final String KIA_BASIC_TOKEN         = "";
    private static final String GENESIS_BASIC_TOKEN     = "";

    private static final String SUB_URL = "";

    public VehicleWebClient request(String cadId, String brand) {

        Map<String, String> requestProps = getProperties(brand);

        responseEntityMono = WebClient
                                .create()
                                .get()
                                .uri(requestProps.get("baseUrl") + SUB_URL, cadId)
                                .header(AUTHORIZATION, requestProps.get("basicToken"))
                                .header(CONTENT_TYPE, "application/json")
                                .exchange()
                                .flatMap(res -> res.toEntity(String.class));

        return this;
    }

    public boolean isInvalidCarId(){
        return responseEntityMono.block().getStatusCodeValue() != HttpStatus.OK.value();
    }


    private static Map<String, String> getProperties(String brand){
        Map<String, String> result = new HashMap<>();
        if("BLU".equals(brand)){
            result.put("basicToken", BLUELINK_BASIC_TOKEN);
            result.put("baseUrl", "");
        }else if("UVO".equals(brand)){
            result.put("basicToken", KIA_BASIC_TOKEN);
            result.put("baseUrl", "");
        }else if("GEN".equals(brand)){
            result.put("basicToken", GENESIS_BASIC_TOKEN);
            result.put("baseUrl", "");
        }else{
            result.put("basicToken", BLUELINK_BASIC_TOKEN);
            result.put("baseUrl", "");
        }
        return result;
    }
    private static String getBrandBaseUrl(String brand){
        return null;

    }

    private static String getBasicToken(String brand){
        return null;

    }

//    public static int request(DeviceMapping deviceMapping){
//        Mono<> response = null;
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
