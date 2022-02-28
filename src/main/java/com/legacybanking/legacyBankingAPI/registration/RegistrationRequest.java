package com.legacybanking.legacyBankingAPI.registration;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
@Getter
@EqualsAndHashCode
@AllArgsConstructor
@ToString
public class RegistrationRequest {
    private final String firstName;
    private final String lastName;
    private final String password;
    private final String email;
    private final String country;
    private final String state;
    private final String city;
    private final Long zipcode;
    private final LocalDate dob;
    private String socialSecurity;




}
