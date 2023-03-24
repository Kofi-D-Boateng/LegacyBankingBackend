package com.legacybanking.legacyBankingAPI.controller;


import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.model.InvocationType;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.google.gson.Gson;
import com.legacybanking.legacyBankingAPI.config.aws.AWSConfig;
import com.legacybanking.legacyBankingAPI.config.securityConfig.MessageBrokerConfiguration;
import com.legacybanking.legacyBankingAPI.models.LambdaPayload;
import com.legacybanking.legacyBankingAPI.models.transaction.TransactionNotification;
import com.legacybanking.legacyBankingAPI.models.transaction.accountTransfer.AccountTransferRequest;
import com.legacybanking.legacyBankingAPI.models.transaction.atmTransaction.ATMTransactionRequest;
import com.legacybanking.legacyBankingAPI.models.transaction.vendorTransaction.VendorTransactionRequest;
import com.legacybanking.legacyBankingAPI.services.TransactionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class TransactionListener {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private final AWSConfig awsConfig;

    public static final String FUNCTION = "insertNotification";

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
        TransactionNotification notification = transactionService.accountTransfer(transaction);
        log.info("NOTIFICATION: {}",notification);
        if(notification != null){
            InvokeRequest request = new InvokeRequest()
                    .withInvocationType(InvocationType.Event)
                    .withPayload(new Gson().toJson(new LambdaPayload(FUNCTION,notification)))
                    .withFunctionName("notifications");
            awsConfig.awsLambda().invoke(request);
        }else{
            System.out.println("[ERROR]: Could not create a notification for transaction.");
        }
    }
}
