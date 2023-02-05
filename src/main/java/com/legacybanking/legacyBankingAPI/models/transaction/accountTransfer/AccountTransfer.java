package com.legacybanking.legacyBankingAPI.models.transaction.accountTransfer;

import com.legacybanking.legacyBankingAPI.enums.CardType;
import com.legacybanking.legacyBankingAPI.enums.TransactionType;
import com.legacybanking.legacyBankingAPI.models.user.Customer;
import com.legacybanking.legacyBankingAPI.models.abstractClass.Transaction;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "account_transfer"
)
public class AccountTransfer extends Transaction {
    @Column(
            name = "recipient_of_transfer",
            nullable = false
    )
    private String recipient;

    @Column(
            name = "transferred_out_of_network",
            nullable = false
    )
    private Boolean isTransferringToOutsideBank;

    public AccountTransfer(Double amount, Customer customer, String accountNumber, String location, TransactionType type, LocalDateTime dateOfTransaction, String recipient, Boolean isTransferringToOutsideBank
    , LocalDateTime dateTransactionPosted, CardType cardType) {
        super(amount, customer, accountNumber, location, type, dateOfTransaction,dateTransactionPosted,cardType);
        this.recipient = recipient;
        this.isTransferringToOutsideBank = isTransferringToOutsideBank;
    }
}
