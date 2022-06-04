package com.legacybanking.legacyBankingAPI.controller;


import com.legacybanking.legacyBankingAPI.models.TransactionModel;
import com.legacybanking.legacyBankingAPI.models.TransactionNotification;
import com.legacybanking.legacyBankingAPI.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
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
    @PutMapping("/vendor-transaction")
    public String vendorTransaction(@RequestBody TransactionModel transaction) throws RuntimeException{

        boolean result = transactionService.vendorTransaction(transaction);
        if(!result){
            return DECLINED;
        }
        return ACCEPTED;
    }
    @PutMapping("/atm-transaction")
    public String atmTransaction(@RequestBody TransactionModel transaction) {
        log.info("PARAMS: {}",transaction);
        boolean result = transactionService.atmTransaction(transaction);
        if(!result){
            return DECLINED;
        }
        return ACCEPTED;
    }

    @PutMapping("/account-transfer")
    public TransactionNotification accountTransfer(@RequestBody TransactionModel transaction) {
       return transactionService.accountTransfer(transaction);
    }
}
