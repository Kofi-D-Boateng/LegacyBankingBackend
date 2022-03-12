package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.Repos.BankRepo;
import com.legacybanking.legacyBankingAPI.models.Bank;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Repository
@AllArgsConstructor
public class BankService {

    @Autowired
    private final BankRepo bankRepo;

    public List<Bank> getBank(){
        return bankRepo.findAll();
    }
}
