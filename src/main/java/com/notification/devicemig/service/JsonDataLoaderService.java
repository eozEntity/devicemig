package com.notification.devicemig.service;

import com.notification.devicemig.model.vo.DeviceVO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JsonDataLoaderService implements DataLoaderService{

    @Override
    public List<DeviceVO> getData(String brand) throws ParseException, IOException {
        List<DeviceVO> result = new ArrayList<>();
        ClassPathResource resource = new ClassPathResource("GEN.json");

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject)jsonParser.parse(new FileReader(resource.getFile()));

        JSONArray carIds = (JSONArray) jsonObject.get("carIds");


        for(int i=0;i<carIds.size();i++){
            DeviceVO deviceVO = DeviceVO.jsonObjectToVo((JSONObject) carIds.get(i), brand);

            result.add(deviceVO);
        }

        return result;
    }
}
