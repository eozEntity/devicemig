package com.notification.devicemig;

import com.notification.devicemig.request.VehicleWebClient;
import com.notification.devicemig.service.DeviceMigService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.FileNotFoundException;
import java.io.IOException;

@SpringBootApplication
@RequiredArgsConstructor
public class DevicemigApplication {

	private final DeviceMigService deviceMigService;

	public static void main(String[] args) {
		SpringApplication.run(DevicemigApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() throws InterruptedException, ParseException, IOException {
		deviceMigService.verifyByJson();
	}
}
