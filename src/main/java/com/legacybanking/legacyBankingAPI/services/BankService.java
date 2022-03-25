package com.legacybanking.legacyBankingAPI.services;

import com.legacybanking.legacyBankingAPI.Repos.BankRepo;
import com.legacybanking.legacyBankingAPI.models.Bank;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@Repository
@AllArgsConstructor
@Slf4j
public class BankService {

    @Autowired
    private final BankRepo bankRepo;

    public List<Bank> getBank(){
       List<Bank> banks = bankRepo.findAll(); 
        log.info("BANKS: {}",banks);
        return banks;
    }
}
