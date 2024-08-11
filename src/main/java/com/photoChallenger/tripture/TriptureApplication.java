package com.photoChallenger.tripture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TriptureApplication {

	public static void main(String[] args) {
		SpringApplication.run(TriptureApplication.class, args);
	}

}
