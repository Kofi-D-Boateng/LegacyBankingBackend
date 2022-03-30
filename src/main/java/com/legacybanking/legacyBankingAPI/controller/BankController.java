package com.legacybanking.legacyBankingAPI.controller;

import com.legacybanking.legacyBankingAPI.models.Bank;
import com.legacybanking.legacyBankingAPI.services.BankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(path = "/api/v1/bank/")
public class BankController {

    private final BankService bankService;

    @Autowired
    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("info")
    public Bank getBankInfo(){
        List<Bank> banks = bankService.getBank();
        return banks.get(0);
    }
}
