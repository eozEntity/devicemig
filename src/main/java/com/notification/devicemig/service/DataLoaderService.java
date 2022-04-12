package com.notification.devicemig.service;

import com.notification.devicemig.model.vo.DeviceVO;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface DataLoaderService {
    List<DeviceVO> getData(String brand) throws ParseException, IOException;
}
