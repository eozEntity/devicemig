package com.notification.devicemig.request;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

class VehicleWebClientTest {

    private VehicleWebClient vehicleWebClient;
    private MockWebServer mockWebServer;
//
//    private VehicleWebClient vehicleWebClient;

    // 최초 한번만 실행
    // take care of creating and shutting down the MockWebServer.
//    @BeforeAll
//    static void setUp() throws IOException {
//        vehicleMockServer = new MockWebServer();
//        vehicleMockServer.start();
//    }
//
    @BeforeEach
    void init() throws IOException {
        mockWebServer = new MockWebServer();

        String baseUrl = mockWebServer.url("/").url().toString();
        vehicleWebClient = new VehicleWebClient();

    }

    @AfterEach
    void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @Disabled
    void test() throws InterruptedException {
        String json = "{\"name\":\"jiwoo\",\"age\":7}";

        mockWebServer.enqueue(
                new MockResponse().setResponseCode(200)
                        .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .setBody(json)
        );

        boolean result = vehicleWebClient.request("carId", "BLU")
                                        .isInvalidCarId();
//
        RecordedRequest request = mockWebServer.takeRequest();
//
        assertThat(request.getMethod()).isEqualTo("GET");
        assertThat(result).isEqualTo(false);

    }

}