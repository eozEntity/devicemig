package com.notification.devicemig.service;

import com.notification.devicemig.model.vo.DeviceVO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExcelDataLoaderService implements DataLoaderService{
    @Override
    public List<DeviceVO> getData(String brand) {
        return null;
    }
}
