package com.joosulsa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class JoosulsaApplication {

	public static void main(String[] args) {
		SpringApplication.run(JoosulsaApplication.class, args);
	}

}
