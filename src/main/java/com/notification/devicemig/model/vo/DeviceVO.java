package com.notification.devicemig.model.vo;

import lombok.Builder;
import lombok.Data;
import org.json.simple.JSONObject;

@Data
@Builder
public class DeviceVO {
    private String carId;
    private String brand;



    public static DeviceVO jsonObjectToVo(JSONObject obj, String brand){
        return DeviceVO.builder()
                .brand(brand)
                .carId((String)obj.get("car_id"))
                .build();

    }

}
