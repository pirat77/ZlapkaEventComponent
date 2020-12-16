package com.codecool.zlapka.eventcomponent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication
class EventComponent {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(EventComponent.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", "10801"));
		app.run(args);
	}
}
