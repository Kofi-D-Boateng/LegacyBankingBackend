package com.legacybanking.legacyBankingAPI.models.customer;

import com.legacybanking.legacyBankingAPI.enums.BankAccountType;
import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.enums.CreditType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Registration {
    private String firstName;
    private String lastName;
    private String password;
    private LocalDate dob;
    private String email;
    private String country;
    private String state;
    private Long zipcode;
    private String socialSecurity;
    private Double capital;
    private Long phoneNumber;
    private BankAccountType bankAccountType;
    private CardType cardType;
    private CreditType creditType;
    private Long customerPin;
    private Double annualPercentageRate;
    private Double interestRate;
    private Double maxAllowedContribution;
    private Double minimumBalanceAllowed;

}
