package com.legacybanking.legacyBankingAPI.models.accounts;

import com.legacybanking.legacyBankingAPI.enums.BankAccountType;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Account;
import com.legacybanking.legacyBankingAPI.models.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "savings_account")
public class SavingsAccount extends Account {
    @Column(
            name = "interest_rate",
            columnDefinition = "Decimal(10,2) default '0.00'",
            nullable = false
    )
    private Double interestRate;
    @Column(
            name = "max_allowed_contrib",
            columnDefinition = "Decimal(10,2) default '0.00'",
            nullable = false
    )
    private Double maxAllowedContribution;
    public SavingsAccount(Customer customer, String accountNumber, String routingNumber,
                          BankAccountType bankAccountType, Double capital, Boolean isEnabled,
                          Double interestRate,Double maxAllowedContribution) {
        super(customer, accountNumber, routingNumber, bankAccountType, capital, isEnabled);
        this.interestRate = interestRate;
        this.maxAllowedContribution = maxAllowedContribution;
    }
}
