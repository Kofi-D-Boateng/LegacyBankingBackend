package com.legacybanking.legacyBankingAPI.controller;


import com.legacybanking.legacyBankingAPI.models.transaction.*;

import com.legacybanking.legacyBankingAPI.models.transaction.accountTransfer.AccountTransferRequest;
import com.legacybanking.legacyBankingAPI.models.transaction.atmTransaction.ATMTransactionRequest;
import com.legacybanking.legacyBankingAPI.models.transaction.vendorTransaction.VendorTransactionRequest;
import com.legacybanking.legacyBankingAPI.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/secure/transaction/")
@AllArgsConstructor
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    private final static String DECLINED = "APPROVED";
    private final static String ACCEPTED = "DENIED";
    @PutMapping("vendor-transaction")
    public String vendorTransaction(@RequestBody VendorTransactionRequest vendorTransaction) throws RuntimeException{

        boolean result = transactionService.vendorTransaction(vendorTransaction);
        return result ? ACCEPTED : DECLINED;
    }
    @PutMapping("atm-transaction")
    public String atmTransaction(@RequestBody ATMTransactionRequest atmTransfer) {
        log.info("PARAMS: {}",atmTransfer);
        boolean result = transactionService.atmTransaction(atmTransfer);
        return result ? ACCEPTED : DECLINED;
    }

    @PutMapping("account-transfer")
    public TransactionNotification accountTransfer(@RequestBody AccountTransferRequest transaction) {
        log.info("TRANSACTION: {}",transaction);
       return transactionService.accountTransfer(transaction);
    }
}
