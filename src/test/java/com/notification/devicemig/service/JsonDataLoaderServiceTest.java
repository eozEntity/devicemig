package com.notification.devicemig.service;

import com.notification.devicemig.DevicemigApplication;
import com.notification.devicemig.model.vo.DeviceVO;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class JsonDataLoaderServiceTest {
    @MockBean
    private DevicemigApplication devicemigApplication;

    @Autowired
    private DataLoaderService jsonDataLoaderService;

    @Test
    @DisplayName("json 파일에서 데이터를 읽어 온다.")
    void Json_파일_데이터_확인() throws ParseException, IOException {
        int expectedSize = 4;

        List<DeviceVO> data = jsonDataLoaderService.getData("GEN");

        assertThat(data.size()).isEqualTo(expectedSize);

    }
}