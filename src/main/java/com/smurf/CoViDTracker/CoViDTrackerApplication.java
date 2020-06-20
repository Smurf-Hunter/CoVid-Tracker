package com.smurf.CoViDTracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CoViDTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoViDTrackerApplication.class, args);
	}

}
