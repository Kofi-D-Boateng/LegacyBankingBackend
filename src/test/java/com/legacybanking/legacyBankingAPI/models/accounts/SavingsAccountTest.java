package com.legacybanking.legacyBankingAPI.models.accounts;

import com.legacybanking.legacyBankingAPI.enums.BankAccountType;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SavingsAccountTest {
    final SavingsAccount savingsAccount = new SavingsAccount(new Customer(),"","", BankAccountType.SAVINGS,10000.00D,true,15.99D,5000.00D);
    @Test
    void deposit() {
        assertNotNull(savingsAccount);
        Double depositAmt = 1500D;
        if(!savingsAccount.getIsEnabled()){
            return;
        }
        Double currAmt = savingsAccount.getCapital();
        assertTrue(savingsAccount.deposit(depositAmt));
        assertEquals(currAmt+depositAmt,savingsAccount.getCapital());
    }

    @Test
    void withdraw() {
        assertNotNull(savingsAccount);
        Double withdrawAmt = 1500.00D;
        if(!savingsAccount.getIsEnabled()){
            return;
        }
        Double currAmt = savingsAccount.getCapital();
        Double taxedAmt = (1+(savingsAccount.getInterestRate()/100.00)) * withdrawAmt;
        assertTrue(savingsAccount.withdraw(withdrawAmt));
        assertEquals((currAmt-taxedAmt),savingsAccount.getCapital());
    }
}