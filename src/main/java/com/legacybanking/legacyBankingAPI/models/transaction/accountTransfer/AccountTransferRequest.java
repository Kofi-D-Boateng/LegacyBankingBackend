package com.legacybanking.legacyBankingAPI.models.transaction.accountTransfer;

import com.legacybanking.legacyBankingAPI.enums.BankAccountType;
import com.legacybanking.legacyBankingAPI.enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountTransferRequest {
    private String email;
    private String accountNumber;
    private BankAccountType bankAccountType;
    private String emailOfTransferee;
    private Long PhoneNumberOfTransferee;
    private Double amount;
    private TransactionType transactionType;
    private LocalDateTime dateOfTransaction;

}