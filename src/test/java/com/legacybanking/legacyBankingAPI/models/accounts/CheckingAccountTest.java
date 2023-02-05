package com.legacybanking.legacyBankingAPI.models.accounts;

import com.legacybanking.legacyBankingAPI.enums.BankAccountType;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckingAccountTest {
    final CheckingAccount checkingAccount = new CheckingAccount(new Customer(),"","", BankAccountType.CHECKING,1500D,true,30.00);
    @Test
    void deposit() {
        assertNotNull(checkingAccount);
        Double depositAmt = 1500D;
        assertTrue(checkingAccount.deposit(depositAmt));
    }

    @Test
    void withdraw() {
        assertNotNull(checkingAccount);
        Double withdrawAmt = 1500D;
        assertFalse(checkingAccount.withdraw(withdrawAmt));
    }
}