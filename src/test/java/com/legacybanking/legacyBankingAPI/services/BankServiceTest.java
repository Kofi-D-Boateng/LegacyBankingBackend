package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.Repos.BankRepo;
import com.legacybanking.legacyBankingAPI.models.Bank;
import com.legacybanking.legacyBankingAPI.models.Branch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class BankServiceTest {

    Bank bank;
    Branch branch;

    @Mock
    BankRepo bankRepo;

    @InjectMocks
    BankService bankService;

    @BeforeEach
    void setUp() {
        branch = new Branch(1L,"Legacy Bank West Branch","The United States",
                "California","63452",new Bank(),500000.25D,73.34D,13.45D);
        List<Branch> list = new ArrayList<>();
        list.add(branch);
        bank = new Bank(1L,"Legacy Bank","The United States",
                "New York","72704",1000000D,list);
    }

    @Test
    void getBank() {
        List<Bank> list = new ArrayList<>();
        list.add(bank);
        when(bankRepo.findAll()).thenReturn(list);

        List<Bank> bank = bankService.getBank();

        assertEquals(1,bank.size());

    }
}