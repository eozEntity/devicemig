package com.notification.devicemig;

import com.notification.devicemig.date.Date;
import com.notification.devicemig.date.DateProvider;
import com.notification.devicemig.service.DeviceMigService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class DevicemigApplication {

	private final DeviceMigService deviceMigService;

	public static void main(String[] args) {
		SpringApplication.run(DevicemigApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void init() throws InterruptedException {
		deviceMigService.verify();
	}

}
