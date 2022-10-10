package com.example.springboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Collections;

@EnableJpaAuditing
@SpringBootApplication
public class SpringboardApplication {
	public static void main(String[] args) {
		System.getenv().forEach((k, v) -> {
			System.out.println(k + ":" + v);
		});

		String envPort = System.getenv("PORT");
		SpringApplication app = new SpringApplication(SpringboardApplication.class);
		app.setDefaultProperties(Collections.singletonMap("server.port", envPort));

		app.run(args);
	}
}
