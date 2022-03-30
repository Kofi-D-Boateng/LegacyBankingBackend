package com.legacybanking.legacyBankingAPI.controller;


import com.legacybanking.legacyBankingAPI.models.TransactionModel;
import com.legacybanking.legacyBankingAPI.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/secure/transaction/")
@AllArgsConstructor
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    private final static String DECLINED = "Transaction was declined.";
    private final static String ACCEPTED = "Transaction was approved.";
    @PostMapping("/vendor-transaction")
    public String vendorTransaction(@RequestBody TransactionModel transaction) throws RuntimeException{

        boolean result = transactionService.vendorTransaction(transaction);
        if(!result){
            return DECLINED;
        }
        return ACCEPTED;
    }
    @PostMapping("/atm-transaction")
    public String atmTransaction(@RequestBody TransactionModel transaction) {
        log.info("PARAMS: {}",transaction);
        boolean result = transactionService.atmTransaction(transaction);
        if(!result){
            return DECLINED;
        }
        return ACCEPTED;
    }

    @PostMapping("/account-transfer")
    public String accountTransfer(@RequestBody TransactionModel transaction) {
        boolean result = transactionService.accountTransfer(transaction);
        if(!result){
            return DECLINED;
        }
        return ACCEPTED;
    }
}
