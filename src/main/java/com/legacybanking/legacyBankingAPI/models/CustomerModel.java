package com.legacybanking.legacyBankingAPI.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel {
    private String firstName;
    private String lastName;
    private String password;
    private LocalDate dob;
    private String email;
    private String country;
    private String state;
    private Long zipcode;
    private String socialSecurity;
    private double capital;

    public CustomerModel(String password, String email) {
        this.password = password;
        this.email = email;
    }
}
