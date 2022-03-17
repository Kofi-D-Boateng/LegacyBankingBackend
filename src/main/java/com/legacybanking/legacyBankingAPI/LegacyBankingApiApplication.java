package com.legacybanking.legacyBankingAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@EnableJpaRepositories
@EntityScan("com.legacybanking.legacyBankingAPI.models")
public class LegacyBankingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(LegacyBankingApiApplication.class, args);
	}

}
