package com.legacybanking.legacyBankingAPI.models.accounts;

import com.legacybanking.legacyBankingAPI.Interfaces.AccountMethods;
import com.legacybanking.legacyBankingAPI.enums.BankAccountType;
import com.legacybanking.legacyBankingAPI.enums.CreditType;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Account;
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
@Table(name = "credit_account")
public class CreditAccount extends Account implements AccountMethods {
    @Column(
            name = "annual_percentage_rate",
            columnDefinition = "Decimal(10,2) default '0.00'",
            nullable = false
    )
    private Double annualPercentageRate;
    @Column(
            name = "current_credit_usage",
            columnDefinition = "Decimal(10,2) default '0.00'",
            nullable = false
    )
    private Double usedCredit;
    @Column(
            name = "minimum_payment_needed",
            columnDefinition = "Decimal(10,2) default '0.00'",
            nullable = false
    )
    private Double minimumPayment;
    @Column(
            name = "credit_account_type",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private CreditType creditType;

    public CreditAccount(Customer customer, String accountNumber, String routingNumber, BankAccountType bankAccountType, Double capital, Boolean isEnabled, Double annualPercentageRate, CreditType creditType) {
        super(customer, accountNumber, routingNumber, bankAccountType, capital, isEnabled);
        this.annualPercentageRate = annualPercentageRate;
        this.usedCredit = 0.0D;
        this.minimumPayment = 0.0D;
        this.creditType = creditType;
    }

    @Override
    public boolean deposit(Double amount) {
        this.usedCredit-=amount;
        return true;
    }

    @Override
    public boolean withdraw(Double amount) {
        Double currCapital = this.getCapital();
        if(usedCredit+amount > currCapital){
            return false;
        }
        usedCredit+=amount;
        return true;
    }
}
