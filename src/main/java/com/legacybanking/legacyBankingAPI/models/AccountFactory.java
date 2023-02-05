package com.legacybanking.legacyBankingAPI.models;

import com.legacybanking.legacyBankingAPI.enums.CreditType;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Account;
import com.legacybanking.legacyBankingAPI.enums.BankAccountType;
import com.legacybanking.legacyBankingAPI.models.accounts.CheckingAccount;
import com.legacybanking.legacyBankingAPI.models.accounts.CreditAccount;
import com.legacybanking.legacyBankingAPI.models.accounts.SavingsAccount;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AccountFactory {
    public Account createAccount(BankAccountType type, Customer customer, Double interestRate, Double maxAllowedContribution, Double annualPercentageRate, Double minimumBalanceAllowed, CreditType creditType){
        if(type == null) return null;
        else if(type.equals(BankAccountType.CHECKING)){
            StringBuilder stringAN = new StringBuilder("1200"), stringRN = new StringBuilder("0533");
            int randomAccountNumber = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
            int randomRoutingNumber = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
            stringAN.append(randomAccountNumber);
            stringRN.append(randomRoutingNumber);
            return new CheckingAccount(customer,stringAN.toString(),stringRN.toString(),BankAccountType.CHECKING,0.0D,false,minimumBalanceAllowed);
        }
        else if(type.equals(BankAccountType.CREDIT)) {
            StringBuilder stringAN = new StringBuilder("1200"), stringRN = new StringBuilder("0533");
            int randomAccountNumber = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
            int randomRoutingNumber = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
            stringAN.append(randomAccountNumber);
            stringRN.append(randomRoutingNumber);
            return new CreditAccount(customer,stringAN.toString(),stringRN.toString(),BankAccountType.CREDIT,0.0D,false,annualPercentageRate, creditType);
        }
        else if(type.equals(BankAccountType.SAVINGS)) {
            StringBuilder stringAN = new StringBuilder("1200"), stringRN = new StringBuilder("0533");
            int randomAccountNumber = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
            int randomRoutingNumber = (int)(Math.random()*(99999 - 10000 +1)+ 10000);
            stringAN.append(randomAccountNumber);
            stringRN.append(randomRoutingNumber);
            return new SavingsAccount(customer,stringAN.toString(),stringRN.toString(),BankAccountType.SAVINGS,0.0D,false,interestRate,maxAllowedContribution);
        }
        return null;
    }
}
