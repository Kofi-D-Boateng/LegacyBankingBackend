package com.legacybanking.legacyBankingAPI.models.accounts;

import com.legacybanking.legacyBankingAPI.models.abstractClass.Account;
import com.legacybanking.legacyBankingAPI.enums.BankAccountType;
import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "checking_account")
public class CheckingAccount extends Account {
    @Column(
            name = "minimum_balance_allowed",
            columnDefinition = "Decimal(10,2) default '0.00'",
            nullable = false
    )
    private Double minimumBalance;

    public CheckingAccount(Customer customer, String accountNumber, String routingNumber, BankAccountType bankAccountType, Double capital, Boolean isEnabled, double minimumBalance) {
        super(customer, accountNumber, routingNumber, bankAccountType, capital, isEnabled);
        this.minimumBalance = minimumBalance;
    }
}
