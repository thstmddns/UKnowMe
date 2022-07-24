package com.ssafy.uknowme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class UKnowMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(UKnowMeApplication.class, args);
	}

}
