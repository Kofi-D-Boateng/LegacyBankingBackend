package com.legacybanking.legacyBankingAPI.controller;


import com.legacybanking.legacyBankingAPI.models.transaction.*;

import com.legacybanking.legacyBankingAPI.models.transaction.accountTransfer.AccountTransferRequest;
import com.legacybanking.legacyBankingAPI.models.transaction.atmTransaction.ATMTransactionRequest;
import com.legacybanking.legacyBankingAPI.models.transaction.vendorTransaction.VendorTransactionRequest;
import com.legacybanking.legacyBankingAPI.security.securityConfig.MessageBrokerConfiguration;
import com.legacybanking.legacyBankingAPI.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

@Component
@AllArgsConstructor
@Slf4j
public class TransactionController {

    @Autowired
    private TransactionService transactionService;
    private final static String DECLINED = "APPROVED";
    private final static String ACCEPTED = "DENIED";
    @RabbitListener(queues = MessageBrokerConfiguration.VENDOR_TRANSACTION_QUEUE)
    public void vendorTransaction(VendorTransactionRequest vendorTransaction) throws RuntimeException{
        log.info("Result of atm-transaction: {}", transactionService.vendorTransaction(vendorTransaction));
    }
    @RabbitListener(queues = MessageBrokerConfiguration.ATM_TRANSACTION_QUEUE)
    public void atmTransaction(ATMTransactionRequest atmTransfer) {
        log.info("PARAMS: {}",atmTransfer);
        log.info("Result of atm-transaction: {}",transactionService.atmTransaction(atmTransfer));
    }

    @RabbitListener(queues = MessageBrokerConfiguration.ACCOUNT_TRANSFER_QUEUE)
    public void accountTransfer(AccountTransferRequest transaction) {
        log.info("TRANSACTION: {}",transaction);
        RabbitTemplate template = new RabbitTemplate();
        TransactionNotification notification = transactionService.accountTransfer(transaction);
        template.convertAndSend(MessageBrokerConfiguration.NOTIFICATIONS_EXCHANGE,MessageBrokerConfiguration.INSERT_NOTIFICATION_ROUTING_KEY,notification);
    }
}
