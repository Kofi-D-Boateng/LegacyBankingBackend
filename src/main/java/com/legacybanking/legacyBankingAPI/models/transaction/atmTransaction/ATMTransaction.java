package com.legacybanking.legacyBankingAPI.models.transaction.atmTransaction;


import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.enums.TransactionType;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Transaction;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(
        name = "atm_transaction"
)
public class ATMTransaction extends Transaction {
    public ATMTransaction(Double amount, Customer customer, String accountNumber, String location, TransactionType type, LocalDateTime dateOfTransaction, LocalDateTime dateTransactionPosted
    , CardType cardType) {
        super(amount, customer, accountNumber, location, type, dateOfTransaction,dateTransactionPosted,cardType);
    }

}
