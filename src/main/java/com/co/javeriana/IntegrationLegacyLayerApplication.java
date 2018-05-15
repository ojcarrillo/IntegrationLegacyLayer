package com.co.javeriana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@AutoConfigurationPackage
@EnableScheduling
public class IntegrationLegacyLayerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IntegrationLegacyLayerApplication.class, args);
	}
}
