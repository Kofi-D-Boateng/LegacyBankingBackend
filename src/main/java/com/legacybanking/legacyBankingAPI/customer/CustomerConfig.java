package com.legacybanking.legacyBankingAPI.customer;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.util.Calendar.SEPTEMBER;

@Configuration
public class CustomerConfig {

    @Bean
    CommandLineRunner commandLineRunner(CustomerRepository repository){
        return args -> {
            Customer kofi = new Customer(
                    1L,
                    "Kofi Boateng",
                    LocalDate.of(1997, SEPTEMBER, 11),
                    "test@email.com",
                    "United States",
                    "Texas",
                    76012L,
                    "111-11-1111"
            );

            Customer Lucy = new Customer(
                    "Lucy Boateng",
                    LocalDate.of(1997, SEPTEMBER, 11),
                    "test2@email.com",
                    "United States",
                    "Texas",
                    76012L,
                    "111-11-1111"
            );
            repository.saveAll(
                    List.of(kofi, Lucy)
            );
        };
    }

}
