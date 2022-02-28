//package com.legacybanking.legacyBankingAPI.customer;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.time.LocalDate;
//import java.util.List;
//
//import static java.util.Calendar.SEPTEMBER;
//
//@Configuration
//public class CustomerConfig {
//
//    @Bean
//    CommandLineRunner commandLineRunner(CustomerRepository repository){
//        return args -> {
//
//            Customer Lucy = new Customer(
//
//                    "Kofi",
//                    "Boateng",
//                    "Pass",
//                    LocalDate.of(1997, SEPTEMBER, 11),
//                    "test2@email.com",
//                    "United States",
//                    "Texas",
//                    76012L,
//                    "111-11-1111",
//                    CustomerRole.USER
//            );
//            repository.saveAll(
//                    List.of(Lucy)
//            );
//        };
//    }
//
//}
