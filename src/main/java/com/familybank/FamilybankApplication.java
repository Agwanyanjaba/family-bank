package com.familybank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.test.context.EmbeddedKafka;

@EmbeddedKafka(topics = {"payment-validation", "payment-notification"}, partitions = 1)

@SpringBootApplication
public class FamilybankApplication {

	public static void main(String[] args) {
		SpringApplication.run(FamilybankApplication.class, args);
	}

}
